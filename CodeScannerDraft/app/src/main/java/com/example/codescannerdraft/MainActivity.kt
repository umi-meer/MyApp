package com.example.codescannerdraft



private const val CAMERA_REQUEST_CODE = 101

class MainActivity : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupPermissions()
        codeScanner()

    }


    private fun codeScanner() {
        codeScanner = CodeScanner( this, scanner_view)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                runOnUiThread {
                    tv_textView.text = it.text
                }
            }

            errorCallback = ErrorCallback {
                runOnUiThread {
                    Log.e( "Main", "Camera initialization error: ${it.message}")
                }
            }
        }


        scanner_view.setOnClickListener {
            codeScanner.startPreview()
        }
    }

        override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
        }

        override fun onPause() {
            codeScanner.releaseResources()
            super.onPause()
        }

        private fun setupPermissions() {
            val permission = ContextCompat.checkSelfPermission( this,
            android.Manifest.permission.CAMERA)

            if (permission != PackageManager.PERMISSION_GRANTED) {
                makeRequest()
            }
        }

        private fun makeRequest() {
            ActivityCompat.requestPermissions( this,
                arrayOf(android.Manifest.permission.CAMERA),
                CAMERA_REQUEST_CODE)
        }

         override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
             super.onRequestPermissionsResult(requestCode, permissions, grantResults)
             when(requestCode){
               CAMERA_REQUEST_CODE -> {
                   if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                       Toast.makeText( this, "You need the camera permission to be able to use this app", Toast.LENGTH_SHORT).show()
                   }else{
                       //SUCCESSFUL
                   }
               }
            }
        }
}