package spajam2016.haggy.nishinokasa.bluetooth;

import java.util.UUID;

/**
 * BLE GATT define class.
 */
public final class KasaGattAttributes {

    public static final String MANUFACTURER_NAME = "00002a29-0000-1000-8000-00805f9b34fb";

    public static final String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";

    public static final String KASA_SERVICE = "19B10000-E8F2-537E-4F6C-D104768A1214";

    public static final String KASA_CHARA = "19B10001-E8F2-537E-4F6C-D104768A1214";

    public static final UUID UUID_KASA_SERVICE = UUID.fromString(KASA_SERVICE);

    public static final UUID UUID_KASA_CHARA = UUID.fromString(KASA_CHARA);



}
