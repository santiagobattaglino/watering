package com.santiago.battaglino.esp32.domain.model.process

data class Computed(
    var quality: Int?,
    val quality_comment: String?,
    val anticipatory_anxiety: Double?,
    val vigilance: Double?,
    val recovery: Double?,
    val stress_regulation: Double?
)

/*data class Computed(
    val bpm: Double?, // average bpm across session
    val breathingrate: Double?,
    val hr_mad: Double?,
    val ibi: Double?,
    val peak_median_p: Double?,
    val perf: Double?, // used for performance score. less than 0.6 is red, between 0.6 and 1.2 is orange, larger than 1.2 is green
    val pnn20: Double?,
    val pnn50: Double?,
    val ppm_p: Double?,
    val rmssd: Double?, // HRV. Higher means good. TODO If no rmssd, use sdnn instead
    val s: Double?,
    val sd1: Double?,
    @SerializedName("sd1/sd2") val sd1sd2: Double?,
    val sd2: Double?,
    val sdnn: Double?, // Substitute rmssd with this one if is null
    val sdsd: Double?,
    val slope_down: Double?,
    val slope_p: Double?,
    val slope_r: Double?, // used for recovery score. less than -0.003 is green, between -0.003 and -0.001 is orange, bigger than -0.001 is red.
    val slope_up: Double?
)*/

/*
{
  "slope_simple_basline": 0,
  "slope_median_basline": 0,
  "slope_simple_scenario": 0.005584636432341058,
  "slope_median_scenario": 0.008247297974798144,
  "slope_simple_recovery": 0,
  "slope_median_recovery": 0,
  "hist_peak": "5999",
  "hist_peak_l": "5999",
  "hist_peak_r": "1526",
  "hist_peak_val": 2.80072549254462,
  "hist_peak_val_l": 2.80072549254462,
  "hist_peak_val_r": 5.088550965453999,
  "hist_ratio_r_l": 0.2543757292882147,
  "hist_ratio_r_cl": 0.23662583346255234,
  "hist_ratio_r_cr": 0.3711089494163424,
  "hist_ratio_r_c": 0.14449389262380458,
  "hist_peak_distance": 2.287825472909379,
  "hist": "[5999  214  139   57   40  607  801 1526  848  330]",
  "hist_thr": "[2.47389328 2.80072549 3.1275577  3.45438991 3.78122212 4.10805433\n 4.43488654 4.76171876 5.08855097 5.41538318 5.74221539]",
  "median_h": 0.028916259467932227,
  "median_h2": 0.05607656257328299,
  "median_w2": 211.56415377545494,
  "peaks_valid": "[  240   428   739   863  1065  1308  1651  1844  1953  2082  2213  2325\n  2740  2974  3092  3142  3400  3544  3633  3760  3923  4120  4310  4614\n  4719  4809  4918  5112  5463  5704  5941  6051  6182  6397  6506  6602\n  6825  7111  7340  7508  7769  7965  8210  8452  8599  8749  9077  9470\n  9632  9860 10025 10133 10334 10475]",
  "peaks_valid2": "[  226   243   384   418   729   868  1060  1092  1106  1307  1650  1837\n  2093  2101  2255  2289  2315  2729  3077  3139  3398  3758  3915  3919\n  4086  4121  4138  4299  4602  4873  4902  4921  5015  5040  5065  5090\n  5127  5264  5286  5454  5592  5647  5705  5926  5946  5954  6043  6173\n  6181  6261  6266  6387  6443  6506  6595  6676  6688  6804  6831  6958\n  6970  6974  7072  7114  7121  7130  7136  7275  7321  7329  7342  7457\n  7487  7521  7705  7744  7765  7946  7966  8201  8234  8448  8587  8739\n  8780  9071  9084  9470  9622  9857  9944 10023 10035 10087 10097 10125\n 10166 10332 10407 10446 10463 10476 10487]",
  "anticipatory_anxiety": 0,
  "vigilance": 0.05607656257328299,
  "recovery": -0.005584636432341058,
  "stress_regulation": 0.2543757292882147,
  "bpm": 76.71994440583738,
  "ibi": 782.0652173913044,
  "sdnn": 155.6161529245589,
  "sdsd": 135.97385369580758,
  "rmssd": 239.09551787239062,
  "pnn20": 0.9333333333333333,
  "pnn50": 0.7666666666666667,
  "hr_mad": 100.0,
  "sd1": 169.00032873077822,
  "sd2": 99.47082207584516,
  "s": 52812.06258303966,
  "sd1/sd2": 1.6989939884272571,
  "breathingrate": 0.23333333333333334
}
 */