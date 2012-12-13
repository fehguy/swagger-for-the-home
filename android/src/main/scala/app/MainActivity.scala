package app

import org.eatbacon.sfth._

import android.app.Activity
import android.os.Bundle
import android.content.Intent

class MainActivity extends Activity with TypedActivity {
  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.main)

    findView(TR.textview).setText("...")
    startActivity(new Intent(this, classOf[AnalogUpdateActivity]))
  }
}
