package ie.hackaccess.access.accessapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import ie.hackaccess.access.accessapp.classes.Booking
import kotlinx.android.synthetic.main.activity_booking.*

class BookingActivity : AppCompatActivity() {

    val MESSAGES_CHILD = "messages"

    var isReturn: Boolean = false

    var firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var firebaseDatabaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        if (firebaseAuth.currentUser == null){
            finish()
        }

        firebaseUser = firebaseAuth.currentUser!!
        firebaseDatabaseReference = FirebaseDatabase.getInstance().reference

        returnchecked.setOnClickListener { view ->
            if (isReturn) {
                returnLabel.visibility = View.GONE
            } else {
                returnLabel.visibility = View.VISIBLE
            }

            isReturn = !isReturn
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_booking, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_booking -> {
                sendBooking()
                return true
            }
            R.id.home -> {
                finish();
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun sendBooking() {

        val booking = Booking();
        booking.from = departureEditText.text.toString()
        booking.to = arrivalEditText.text.toString()
        booking.date = "16/11/2017"
        booking.timeFrom = "15.30"
        booking.timeFrom = "16.30"
        booking.user = firebaseUser.email
        booking.platform = "Ramp Access"

        firebaseDatabaseReference.child(MESSAGES_CHILD)
                .push().setValue(booking)
        finish()
    }

}
