package spajam2016.haggy.nishinokasa;

import android.app.Notification;
import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import spajam2016.haggy.nishinokasa.bluetooth.BleWrapper;
import spajam2016.haggy.nishinokasa.bluetooth.KasaGattAttributes;

public class KaService extends Service {

    private static final String TAG = KaService.class.getSimpleName();

    private BleWrapper bleWrapper;

    private Thread kasaWatchThread;

    public KaService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            bleWrapper = BleWrapper.getsInstance(this);
            bleWrapper.initialize();
        } catch (Exception e) {
            e.printStackTrace();
            this.stopSelf();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
            if (kasaWatchThread != null) {
                kasaWatchThread.interrupt();
                kasaWatchThread.join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            kasaWatchThread = null;
        }

        if (bleWrapper != null) {
            bleWrapper.close();
            bleWrapper.terminate();
            bleWrapper = null;
        }

        stopForeground(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground();

        kasaWatchThread = new Thread(new KasaTask());
        kasaWatchThread.start();

        return START_STICKY;
    }

    private class KasaTask
            implements Runnable, BleWrapper.IBleScannerListener, BleWrapper.IBleGattListener {

        private boolean isConnected = false;

        private boolean isAitai = false;

        private BluetoothGattCharacteristic targerCharacteristic;

        private BluetoothDevice targetDevice;

        @Override
        public void run() {

            bleWrapper.startScan(this);

            while (!kasaWatchThread.isInterrupted()) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onConnected(BluetoothDevice device) {
            bleWrapper.discoverServices();
        }

        @Override
        public void onDisconnected(BluetoothDevice device) {
            isConnected = false;
            targerCharacteristic = null;
            bleWrapper.stopScan();
            bleWrapper.startScan(this);
        }

        @Override
        public void onServiceDiscovered(BluetoothDevice device, List<BluetoothGattService> supportedGattServices) {
            for (BluetoothGattService service : supportedGattServices) {
                if (service.getUuid().equals(KasaGattAttributes.UUID_KASA_SERVICE)) {
                    targerCharacteristic = service.getCharacteristic(KasaGattAttributes.UUID_KASA_CHARA);
                    if (targerCharacteristic != null) {
                        int value = isAitai ? 1 : 0;
                        bleWrapper.writeCharacteristic(targerCharacteristic, value, BluetoothGattCharacteristic.FORMAT_UINT8, 0);
                    }
                }
            }
        }

        @Override
        public void onReadCharacteristic(BluetoothDevice device, BluetoothGattCharacteristic characteristic) {

        }

        @Override
        public void onWriteCharacteristic(BluetoothDevice device) {

            if (isAitai) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isAitai = false;
                        bleWrapper.discoverServices();
                    }
                }, 3000);
            } else {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        bleWrapper.disconnect();
                        isConnected = false;
                        bleWrapper.startScan(KasaTask.this);
                    }
                }, 3000);
            }
        }

        @Override
        public void onScanResult(BluetoothDevice device, int rssi, byte[] data) {

            if (device.getName() == null) {
                return;
            }

            Log.d(TAG, "---- Scan BLE device : " + device.getName() + ", (" + rssi + ") ----");

            if (isConnected) {
                return;
            }

            if (device.getName().equals("N_Kasa")) {
                if (rssi >= -50) {
                    isConnected = true;
                    isAitai = true;
                    bleWrapper.connect(device, this);
                    Log.d(TAG, "逢いたい");
                }
            }
        }
    }

    // -----------------------------

    private static final int NOTIFY_FOREGROUND_ID = 2016;

    private void startForeground() {
        startForeground(NOTIFY_FOREGROUND_ID, createNotification());
    }

    private Notification createNotification() {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.open_kasa);
        builder.setContentTitle(getText(R.string.app_name));
        builder.setContentText("カサがあなたを待っています...");
        return builder.build();
    }
}
