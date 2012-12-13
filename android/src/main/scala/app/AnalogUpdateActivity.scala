package app

import config.APP
import data._
import service._

import android.app._
import android.os._
import android.widget._
import android.util.Log

import com.handmark.pulltorefresh.library._

import com.wordnik.util.perf.Profile

import scala.collection.mutable.ListBuffer
import scala.collection.JavaConverters._

class AnalogUpdateActivity extends Activity {
  var adapter: ArrayAdapter[String] = _
  var task: UpdateDataTask = _

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main)

    val textView = findViewById(R.id.textview).asInstanceOf[TextView]
    val listView1 = findViewById(R.id.listView).asInstanceOf[PullToRefreshListView]

    listView1.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener[ListView]() {
      override def onRefresh(refreshView: PullToRefreshBase[ListView]) = {
        // Do work to refresh the list here.
        new UpdateDataTask2(refreshView).execute(null)
      }
    })

    adapter = new ArrayAdapter[String](this, android.R.layout.simple_list_item_1, RemoteData.values)
    
    listView1.setAdapter(adapter)

    task = new UpdateDataTask(this)
    task.execute(null)
  }

  override def onDestroy {
  	super.onDestroy
  	if(task != null)
  		task.cancel(true)
  }

  def updateData(items: Array[String]) = Profile("updateData", {
  	adapter.notifyDataSetChanged()
  })

  class UpdateDataTask2(list: PullToRefreshBase[ListView]) extends AsyncTaskImpl[Void, Integer, Long] {
    var data: List[String] = List("yes", "no")
    override protected def doInBackgroundImpl(values: Void*) = {
      RemoteData()
      0
    }
    override protected def onProgressUpdateImpl(progress: Integer*) = {}
    override protected def onPostExecute(result: Long) = {
      list.onRefreshComplete()
      super.onPostExecute(result)
    }
  }
}

class UpdateDataTask(activity: AnalogUpdateActivity) extends AsyncTaskImpl[Void, Integer, Long] {
  var data: List[String] = List.empty
  def this() = this(null)

  override protected def doInBackgroundImpl(values: Void*)  = {
    RemoteData()
    0
  }

  override protected def onProgressUpdateImpl(progress: Integer*) = {
  }

  override protected def onPostExecute(result: Long) = {
    if(activity != null) {
      activity.updateData(data.toArray)
    }
  }
}
