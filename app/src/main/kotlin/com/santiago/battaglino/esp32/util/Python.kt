package com.santiago.battaglino.esp32.util

/*@Throws(IOException::class)
fun doMedFilt() {
    val progressDialog = ProgressDialog.show(this, "", "Please wait...")
    if (!Python.isStarted()) {
        Python.start(AndroidPlatform(this))
    }
    object : Thread() {
        override fun run() {
            try {
                if (!flag_medfiltdata_ready) {
                    // Save to csv
                    val filePath: String =
                        com.neurosmarttech.neurosmartandroid.newSession.NewSession.APP_DIR_PATH + File.separator + "medfilt.csv"

                    // Read input csv file
                    val numDataPoints: Int = input_CSVSamples.size
                    Log.d("num:", numDataPoints.toString())
                    val input = ArrayList<Double>()
                    val times = ArrayList<Double>()
                    val eda_delta_nan = ArrayList<Double>()
                    val indices = DoubleArray(numDataPoints)
                    val cut_off = 0
                    for (i in 0 until numDataPoints) {
                        indices[i] = i.toDouble()
                        val data: CSVSample = input_CSVSamples.get(i)
                        val x: Double = data.getTimeStamp()
                        times.add(x)
                        var y: Double = data.getGSR_skin_conductance()
                        // EDA clean
                        if (y <= com.neurosmarttech.neurosmartandroid.newSession.NewSession.thr_abs) {
                            y = Double.NaN
                        }
                        input.add(y)
                    }
                    Log.d("here:", "x")
                    var input_array: Array<Double?>? = arrayOfNulls(input.size)
                    input_array = input.toArray(input_array)

//                        // Python script
                    val py: Python = Python.getInstance()
                    val pyobj: PyObject = py.getModule("data_cleaning")
                    val output: Array<Double> =
                        pyobj.callAttr("clean", input_array, 5, 0.2, 255, 128).toJava(
                            Array<Double>::class.java
                        )

//                        medFilt_CSVSamples.clear();
//
//                        // Write to csv file
//                        CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath, false));
//                        String header[] = new String[]{"Time", "Skin_conductance"};
//                        csvWriter.writeNext(header);
//                        for (int i = 0; i < output.length; i++) {
//                            CSVSample sample = new CSVSample();
//                            sample.setTimeStamp(times.get(i));
//                            sample.setGSR_skin_conductance(output[i]);
//                            medFilt_CSVSamples.add(sample);
//                            String row[] = new String[]{
//                                    Double.toString(sample.getTimeStamp()), Double.toString(
//                                    sample.getGSR_skin_conductance())
//                            };
//                            csvWriter.writeNext(row);
//                        }
//                        csvWriter.close();
                }
                flag_medfiltdata_ready = true
                Log.d("compute:", "compute")
                recovery_score = computeRecovery(100, 20.0)
                Log.d("score:     ", java.lang.Double.toString(recovery_score))
            } catch (e: java.lang.Exception) {
                Log.e("tag", e.message!!)
            }
            // dismiss the progress dialog
            progressDialog.dismiss()
        }
    }.start()
}*/