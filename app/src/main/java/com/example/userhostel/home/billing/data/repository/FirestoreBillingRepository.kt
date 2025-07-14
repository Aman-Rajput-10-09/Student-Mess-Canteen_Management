package com.example.userhostel.home.billing.data.repository

import com.example.userhostel.home.billing.domain.repository.BillingRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreBillingRepository : BillingRepository {

    private val db = FirebaseFirestore.getInstance()

    override suspend fun getTotalAmount(hostel: String, rollNumber: String): Int {
        var total = 0

        try {
            val departments = listOf("mess", "canteen") // 🔁 both sources

            for (department in departments) {
                val snapshot = db.collection("hostel")
                    .document(hostel)
                    .collection(department)
                    .document("transactions")
                    .collection("entries")
                    .get()
                    .await()

                for (doc in snapshot.documents) {
                    val data = doc.data ?: continue
                    if (data["rollNumber"] == rollNumber) {
                        val price = data["price"] as? Long ?: 0L
                        total += price.toInt()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return total
    }
}
