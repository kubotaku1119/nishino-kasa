package spajam2016.haggy.nishinokasa;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class KaService extends Service {
    public KaService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
