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
import org.achartengine.model.{ TimeSeries, XYMultipleSeriesDataset, RangeCategorySeries }
import org.achartengine.renderer.{ XYMultipleSeriesRenderer, XYSeriesRenderer}

import com.wordnik.util.perf.Profile

class ShowChartActivity extends Activity {
  val colors = Array(
    // Color.parseColor("#00FFFF"),
    Color.parseColor("#7FFFD4"),
    Color.parseColor("#F0FFFF"),
    Color.parseColor("#F5F5DC"),
    Color.parseColor("#FFE4C4"),
    Color.parseColor("#F0F8FF"),
    Color.parseColor("#008B8B"),
    Color.parseColor("#556B2F"),
    Color.parseColor("#FF8C00"),
    Color.parseColor("#E9967A"),
    Color.parseColor("#8FBC8F"),
    Color.parseColor("#483D8B"),
    Color.parseColor("#2F4F4F"),
    Color.parseColor("#00CED1"),
    Color.parseColor("#9400D3"),
    Color.parseColor("#FF1493"),
    Color.parseColor("#696969"),
    Color.parseColor("#1E90FF")
  )

  /** Called when the activity is first created. */
  var mDataset: XYMultipleSeriesDataset = _
  var mRenderer: XYMultipleSeriesRenderer = _
  var values = new java.util.ArrayList[Double]()
  var mChartView: GraphicalView = _
  var timeSeries: TimeSeries = _
  var positions: Array[Int] = Array()

  // chart container
  var layout: LinearLayout = _

  override def onCreate(savedInstanceState: Bundle) = {
    val positionString = getIntent().getStringExtra("position")

    positions = positionString.split(",").map(_.toInt).toArray

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

    mRenderer.setYAxisMin(20)
    mRenderer.setYAxisMax(80)
    mRenderer.setZoomButtonsVisible(true)

    mRenderer.setClickEnabled(true)
    mRenderer.setSelectableBuffer(20)
    mRenderer.setPanEnabled(true)

    Log.d("ShowChartActivity", "position: " + positionString)
    new UpdateChartDataTask(this, positionString).execute(null)
  }

  class UpdateChartDataTask(activity: ShowChartActivity, position: String) extends AsyncTaskImpl[Void, Integer, Long] {
	  var data: List[String] = List.empty

	  override protected def doInBackgroundImpl(values: Void*) = {
      Log.d("ShowChartActivity", positions.mkString(","))
      // get minimum point, in case the series' have different ranges

      val data = for(position <- positions) yield (position, RemoteData.valuesForPosition(position, 250))

      val minPoints = data.map(m => {
        val pos = m._1
        val values = m._2
        (pos, values.head.timestamp)
      }).toMap

      val maxStartPoint = minPoints.values.max

      positions.map(position => {
        val r = new XYSeriesRenderer()
        r.setColor(colors(position))
        r.setPointStyle(PointStyle.CIRCLE)
        r.setFillPoints(true)
        mRenderer.addSeriesRenderer(r)

        val timeSeries = new TimeSeries("input " + position)
        val data = RemoteData.valuesForPosition(position, 250)
        data.foreach(m => timeSeries.add(m.timestamp, m.average))
        activity.mDataset.addSeries(timeSeries)
      })
	    0
	  }

	  override protected def onProgressUpdateImpl(progress: Integer*) = {}

	  override protected def onPostExecute(result: Long) = {
  	  activity.mChartView = ChartFactory.getTimeChartView(activity, mDataset, mRenderer, "H:mm:ss")
	    activity.layout.addView(mChartView)
	  }
	}
}

