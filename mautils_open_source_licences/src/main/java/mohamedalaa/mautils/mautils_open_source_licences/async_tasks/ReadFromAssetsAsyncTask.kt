package mohamedalaa.mautils.mautils_open_source_licences.async_tasks

import android.content.Context
import android.content.res.AssetManager
import android.os.AsyncTask
import android.util.Log
import mohamedalaa.mautils.mautils_open_source_licences.extensions.indexOfOrNull
import mohamedalaa.mautils.mautils_open_source_licences.model.Licence
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.ref.WeakReference

class ReadFromAssetsAsyncTask private constructor(context: Context,
                                                  private val pathInAssets: String,
                                                  private val listener: Listener?)
    : AsyncTask<Void?, Void, List<Licence>?>() {

    companion object {

        private const val NAME_KEY = "{@name"
        private const val AUTHOR_KEY = "{@author"
        private const val LINK_KEY = "{@link"
        private const val ENDING_KEY = "}"

        @JvmStatic
        fun launch(context: Context, pathInAssets: String, listener: Listener, parallelExecution: Boolean = true) {
            val asyncTask = ReadFromAssetsAsyncTask(context, pathInAssets, listener)

            if (parallelExecution) {
                asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
            }else {
                asyncTask.execute()
            }
        }

    }

    private var weakReferenceContext: WeakReference<Context>? = WeakReference(context)

    override fun doInBackground(vararg params: Void?): List<Licence>? {
        val context = weakReferenceContext?.get() ?: return null

        val assetManager = context.applicationContext.assets ?: return null
        val subPathList = assetManager.list(pathInAssets)?.toList()?.filterNotNull()
            ?.filter { it.length > 4 && it.endsWith(".txt") } ?: return null

        val listOfLicences = subPathList.mapNotNull {
            val fullPath = if (pathInAssets.isNotEmpty()) "$pathInAssets/$it" else it

            readTextFile(assetManager, fullPath)
        }

        weakReferenceContext?.clear()

        return listOfLicences
    }

    override fun onPostExecute(result: List<Licence>?) {
        listener?.deliverResult(result)
    }

    /**
     * todo test if there is sub folders isa, also generalized sure foreach isa, corrupted licence -> means has no name should be avoided isa.
     * Acc to ->
     * {@name Gson}
     * {@author Google} // usually found in licence 1st line as in Apache isa.
     * {@link https://github.com/google/gson}
     * // from here starts licence content isa.
     */
    private fun readTextFile(assetManager: AssetManager, fullPath: String): Licence? {
        var licence: Licence? = null

        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(InputStreamReader(assetManager.open(fullPath), "UTF-8"))

            var nameConsumed = false
            var authorConsumed = false
            var linkConsumed = false
            var readFullContent = false

            var licenceName: String? = null
            var licenceAuthor: String? = null
            var link: String? = null
            val licenceContent = StringBuilder()

            reader.forEachLine {
                if (readFullContent.not()) {
                    if (nameConsumed.not()) {
                        val nameIndex = it.indexOfOrNull(NAME_KEY)
                        nameIndex?.apply {
                            val starterIndex = nameIndex.plus(NAME_KEY.length).inc()

                            val endIndex = it.indexOfOrNull(ENDING_KEY) ?: throw RuntimeException()

                            if (starterIndex > endIndex) {
                                throw RuntimeException()
                            }

                            licenceName = it.substring(starterIndex, endIndex)

                            nameConsumed = true

                            return@forEachLine
                        }
                    }

                    if (authorConsumed.not()) {
                        val authorIndex = it.indexOfOrNull(AUTHOR_KEY)
                        authorIndex?.apply {
                            val starterIndex = authorIndex.plus(AUTHOR_KEY.length).inc()
                            val endIndex = it.indexOfOrNull(ENDING_KEY) ?: throw RuntimeException()
                            if (starterIndex > endIndex) {
                                throw RuntimeException()
                            }

                            licenceAuthor = it.substring(starterIndex, endIndex)

                            authorConsumed = true

                            return@forEachLine
                        }
                    }

                    if (linkConsumed.not()) {
                        val linkIndex = it.indexOfOrNull(LINK_KEY)
                        linkIndex?.apply {
                            val starterIndex = linkIndex.plus(LINK_KEY.length).inc()
                            val endIndex = it.indexOfOrNull(ENDING_KEY) ?: throw RuntimeException()
                            if (starterIndex > endIndex) {
                                throw RuntimeException()
                            }

                            link = it.substring(starterIndex, endIndex)

                            linkConsumed = true

                            return@forEachLine
                        }
                    }

                    if (licenceName != null) {
                        readFullContent = true
                    }else {
                        throw RuntimeException()
                    }
                }

                licenceContent.append(it)
            }

            // Check licence isa.
            licenceName?.apply {
                if (licenceContent.isNotEmpty()) {
                    licence = Licence(this, licenceAuthor, link, licenceContent.toString().trim())
                }
            }
        }catch (e: Exception) {
            Log.e("AsyncTask", e.message ?: "exception msg was null")
        }finally {
            kotlin.runCatching { reader?.close() }
        }

        return licence
    }

    interface Listener {
        fun deliverResult(result: List<Licence>?)
    }

}