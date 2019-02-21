package mohamedalaa.mautils.mautils_open_source_licences.async_tasks

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import java.lang.ref.WeakReference

class ReadFromAssetsAsyncTask private constructor(context: Context, private val pathInAssets: String): AsyncTask<Void?, Void, String?>() {

    companion object {

        @JvmStatic
        fun launch(context: Context, pathInAssets: String, parallelExecution: Boolean = true) {
            val asyncTask = ReadFromAssetsAsyncTask(context, pathInAssets)

            if (parallelExecution) {
                asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
            }else {
                asyncTask.execute()
            }
        }

    }

    private var weakReferenceContext: WeakReference<Context>? = WeakReference(context)

    override fun doInBackground(vararg params: Void?): String? {
        val context = weakReferenceContext?.get() ?: return null

        val assetManager = context.applicationContext.assets ?: return null
        val subPathList = assetManager.list(pathInAssets)?.toList()?.filterNotNull()
            ?.filter { it.length > 4 && it.endsWith(".txt") } ?: return null

        // todo filter with .txt at end only isa.

        subPathList.forEach {
            val fullPath = "$pathInAssets/$it"

            Log.e(ReadFromAssetsAsyncTask::class.java.simpleName, "Assets File isa -> $fullPath")
            //assetManager.open(fullPath)
        }

        weakReferenceContext?.clear()

        return null
    }

}