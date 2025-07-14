package com.example.userhostel.home.history.data.repository

import android.util.Log
import com.example.userhostel.home.history.domain.model.Transaction
import com.example.userhostel.home.history.domain.repository.HistoryRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreHistoryRepository() : HistoryRepository {

    private val db = FirebaseFirestore.getInstance()

    override suspend fun getTransactionsForRoll(hostel: String, department: String, rollNumber: String): List<Transaction> {
        return try {
            Log.d("Firestore", "Fetching transactions for hostel: $hostel, department: $department, rollNumber: $rollNumber")

            val snapshot = db.collection("hostel")
                .document(hostel)
                .collection(department)
                .document("transactions")
                .collection("entries")
                .get()
                .await()

            Log.d("Firestore", "Documents fetched: ${snapshot.documents.size}")

            val transactions = snapshot.documents.mapNotNull { doc ->
                val data = doc.data ?: return@mapNotNull null
                Log.d("Firestore", "Checking document: ${doc.id}, data: $data")

                if (data["rollNumber"] == rollNumber) {
                    val transaction = Transaction(
                        rollNumber = data["rollNumber"] as? String ?: "",
                        price = (data["price"] as? Long)?.toInt() ?: 0,
                        date = data["date"] as? String ?: "",
                        time = data["time"] as? String ?: ""
                    )
                    Log.d("Firestore", "Matched transaction: $transaction")
                    transaction
                } else {
                    Log.d("Firestore", "Skipped transaction for rollNumber: ${data["rollNumber"]}")
                    null
                }
            }

            Log.d("Firestore", "Total matched transactions: ${transactions.size}")
            transactions

        } catch (e: Exception) {
            Log.e("Firestore", "Error fetching transactions", e)
            emptyList()
        }
    }
}
