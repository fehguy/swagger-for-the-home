package app

import org.eatbacon.sfth._

import config._
import data._
import app._
import service._

import android.app._
import android.os._
import android.widget._
import android.content._
import android.util.Log
import android.graphics.Color

import java.util.{ ArrayList, Date, Random}

import org.achartengine.{ ChartFactory, GraphicalView }
import org.achartengine.chart.{ PointStyle, TimeChart }
import org.achartengine.model.{ TimeSeries, XYMultipleSeriesDataset }
import org.achartengine.renderer.{ XYMultipleSeriesRenderer, XYSeriesRenderer}

import com.wordnik.util.perf.Profile

class ShowChartActivity extends Activity {
  /** Called when the activity is first created. */
  var mDataset: XYMultipleSeriesDataset = _
  var mRenderer: XYMultipleSeriesRenderer = _
  var values = new java.util.ArrayList[Double]()
  var mChartView: GraphicalView = _
  var time_series: TimeSeries = _

  // chart container
  var layout: LinearLayout = _

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.chart)

    layout = findViewById(R.id.chart).asInstanceOf[LinearLayout]

    // create dataset and renderer
    mDataset = new XYMultipleSeriesDataset()
    mRenderer = new XYMultipleSeriesRenderer()
    mRenderer.setAxisTitleTextSize(16)
    mRenderer.setChartTitleTextSize(20)
    mRenderer.setLabelsTextSize(15)
    mRenderer.setLegendTextSize(15)
    mRenderer.setPointSize(3f)

    val r = new XYSeriesRenderer()
    r.setColor(Color.GREEN)
    r.setPointStyle(PointStyle.CIRCLE)
    r.setFillPoints(true)
    mRenderer.addSeriesRenderer(r)
    mRenderer.setClickEnabled(true)
    mRenderer.setSelectableBuffer(20)
    mRenderer.setPanEnabled(true)

    val position = getIntent().getStringExtra("position")

    time_series = new TimeSeries("input " + position)

    Log.d("ShowChartActivity", "position: " + position)
    new UpdateChartDataTask(this, position).execute(null)
  }

  class UpdateChartDataTask(activity: ShowChartActivity, position: String) extends AsyncTaskImpl[Void, Integer, Long] {
	  var data: List[String] = List.empty

	  override protected def doInBackgroundImpl(values: Void*)  = {
      val data = RemoteData.valuesForPosition(position.toInt, 500)
	    data.foreach(d => time_series.add(d.timestamp, d.average))
	    0
	  }

	  override protected def onProgressUpdateImpl(progress: Integer*) = {
	  }

	  override protected def onPostExecute(result: Long) = {
		  activity.mDataset.addSeries(time_series)
  	  activity.mChartView = ChartFactory.getTimeChartView(activity, mDataset, mRenderer, "H:mm:ss")
	    activity.layout.addView(mChartView)
	  }
	}
}

