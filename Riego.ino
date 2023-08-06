#include <Arduino.h>
#include <WiFi.h>
#include <AsyncTCP.h>
#include <ESPAsyncWebServer.h>
#include <HTTPClient.h>
#include <Arduino_JSON.h>

#define uS_TO_S_FACTOR 1000000ULL
#define Threshold 60
RTC_DATA_ATTR int bootCount = 0;
RTC_DATA_ATTR bool status = 0;     // 0 relay OFF, 1 relay ON
RTC_DATA_ATTR bool deepSleep = 1;  // 0 deepSleep Off, 1 deepSleep ON
RTC_DATA_ATTR int wakeupTimer = 0;
RTC_DATA_ATTR int run = 120;     // run 2 min
RTC_DATA_ATTR int every = 7200;  // every 2 hours
touch_pad_t touchPin;

HTTPClient http;
AsyncWebServer server(80);

const char* ssid = "SB";            // Your WiFi SSID
const char* password = "12345678";  // Your WiFi Password

String sensorReadings;
int sensorReadingsArr[3];

void setup() {
  Serial.begin(115200);
  delay(1000);
  pinMode(GPIO_NUM_33, OUTPUT);
  setUpWiFi();
  if (WiFi.status() == WL_CONNECTED) {
    retrieveConfig();
    setUpServer();
  }
  doRelay();
  wakeUpTimerAndSleep();
}

void setUpWiFi() {
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);
  if (WiFi.waitForConnectResult() != WL_CONNECTED) {
    Serial.printf("WiFi Failed!\n");
    return;
  }
  Serial.println("IP Address: ");
  Serial.println(WiFi.localIP());
  Serial.println("MAC Address: ");
  Serial.println(WiFi.macAddress());
}

void retrieveConfig() {
  http.begin("http://192.168.0.199:13276/config");
  int httpResponseCode = http.GET();
  String payload = "{}";
  if (httpResponseCode > 0) {
    Serial.print("Config HTTP Response code: ");
    Serial.println(httpResponseCode);
    payload = http.getString();
    JSONVar json = JSON.parse(payload);
    if (JSON.typeof(json) == "undefined") {
      Serial.println("Parsing input failed!");
    }
    Serial.print("JSON object: ");
    Serial.println(json);

    // json.keys() can be used to get an array of all the keys in the object
    JSONVar keys = json.keys();

    for (int i = 0; i < keys.length(); i++) {
      JSONVar value = json[keys[i]];
      sensorReadingsArr[i] = int(value);
    }

    Serial.print("deepSleep: ");
    Serial.println(sensorReadingsArr[0]);
    deepSleep = sensorReadingsArr[0];

    Serial.print("every: ");
    Serial.println(sensorReadingsArr[1]);
    every = sensorReadingsArr[1];

    Serial.print("run: ");
    Serial.println(sensorReadingsArr[2]);
    run = sensorReadingsArr[2];

    Serial.print("status: ");
    Serial.println(sensorReadingsArr[3]);
    status = sensorReadingsArr[3];
  } else {
    Serial.print("Config Error code: ");
    Serial.println(httpResponseCode);
  }
  http.end();
}

void setUpServer() {
  // Listen for GET {IP}/
  server.on("/", HTTP_GET, [](AsyncWebServerRequest* request) {
    request->send_P(200, "application/json", "{}");
  });

  // Listen for GET {IP}/config?deepSleep=0&every=0&run=0&status=0
  server.on("/config", HTTP_GET, [](AsyncWebServerRequest* request) {
    String getRun = "0";
    String getEvery = "0";
    String getStatus = "0";
    String getDeepSleep = "0";
    if (request->hasParam("deepSleep") && request->hasParam("every") && request->hasParam("run") && request->hasParam("status")) {

      getDeepSleep = request->getParam("deepSleep")->value();
      deepSleep = getDeepSleep.toInt();
      Serial.println("getDeepSleep: " + getDeepSleep);

      getEvery = request->getParam("every")->value();
      every = getEvery.toInt();
      Serial.println("getEvery: " + getEvery);

      getRun = request->getParam("run")->value();
      run = getRun.toInt();
      Serial.println("getRun: " + getRun);

      getStatus = request->getParam("status")->value();
      status = getStatus.toInt();
      Serial.println("getStatus: " + getStatus);

      doRelay();
      wakeUpTimerAndSleep();
    } else {
      getDeepSleep = "0";
      getEvery = "0";
      getRun = "0";
      getStatus = "0";
    }

    AsyncResponseStream* response = request->beginResponseStream("application/json");
    DynamicJsonDocument json(1024);
    json["deepSleep"] = getDeepSleep;
    json["every"] = getEvery;
    json["run"] = getRun;
    json["status"] = getStatus;
    serializeJson(json, *response);
    request->send(response);
  });

  server.begin();
}

void doRelay() {
  if (deepSleep == 1) {
    gpio_hold_dis(GPIO_NUM_33);
    gpio_deep_sleep_hold_dis();
  }
  if (status == 1) {
    stop();
  } else {
    start();
  }
  if (deepSleep == 1) {
    gpio_hold_en(GPIO_NUM_33);
    gpio_deep_sleep_hold_en();
  }
}

void start() {
  Serial.println("start");
  wakeupTimer = run;
  status = 1;
  digitalWrite(GPIO_NUM_33, HIGH);
}

void stop() {
  Serial.println("stop");
  wakeupTimer = every;
  status = 0;
  digitalWrite(GPIO_NUM_33, LOW);
}

void wakeUpTimerAndSleep() {
  if (deepSleep == 1) {
    ++bootCount;
    Serial.println("Boot number: " + String(bootCount));
    print_wakeup_reason();
    print_wakeup_touchpad();
    touchAttachInterrupt(GPIO_NUM_15, callback, Threshold);
    esp_sleep_enable_touchpad_wakeup();
    esp_sleep_enable_timer_wakeup(wakeupTimer * uS_TO_S_FACTOR);
    Serial.println("Sleep for " + String(wakeupTimer) + " seconds");
    Serial.flush();
    esp_deep_sleep_start();
  }
}

void callback() {
  Serial.print("wakeup touch callback");
}

void print_wakeup_reason() {
  esp_sleep_wakeup_cause_t wakeup_reason;
  wakeup_reason = esp_sleep_get_wakeup_cause();
  switch (wakeup_reason) {
    case ESP_SLEEP_WAKEUP_EXT0: Serial.println("Wakeup caused by external signal using RTC_IO"); break;
    case ESP_SLEEP_WAKEUP_EXT1: Serial.println("Wakeup caused by external signal using RTC_CNTL"); break;
    case ESP_SLEEP_WAKEUP_TIMER: Serial.println("Wakeup caused by timer"); break;
    case ESP_SLEEP_WAKEUP_TOUCHPAD: Serial.println("Wakeup caused by touchpad"); break;
    case ESP_SLEEP_WAKEUP_ULP: Serial.println("Wakeup caused by ULP program"); break;
    default: Serial.printf("Wakeup was not caused by deep sleep: %d\n", wakeup_reason); break;
  }
}

void print_wakeup_touchpad() {
  touchPin = esp_sleep_get_touchpad_wakeup_status();

  switch (touchPin) {
    case 0: Serial.println("Touch detected on GPIO 4"); break;
    case 1: Serial.println("Touch detected on GPIO 0"); break;
    case 2: Serial.println("Touch detected on GPIO 2"); break;
    case 3: Serial.println("Touch detected on GPIO 15"); break;
    case 4: Serial.println("Touch detected on GPIO 13"); break;
    case 5: Serial.println("Touch detected on GPIO 12"); break;
    case 6: Serial.println("Touch detected on GPIO 14"); break;
    case 7: Serial.println("Touch detected on GPIO 27"); break;
    case 8: Serial.println("Touch detected on GPIO 33"); break;
    case 9: Serial.println("Touch detected on GPIO 32"); break;
    default: Serial.println("Wakeup not by touchpad"); break;
  }
}

void loop() {
}