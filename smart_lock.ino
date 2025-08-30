#include <BluetoothSerial.h>
BluetoothSerial SerialBT;

#define LOCK_PIN 5  // PIN المتصل بالريليه / السيرفو

void setup() {
  pinMode(LOCK_PIN, OUTPUT);
  digitalWrite(LOCK_PIN, LOW); // مقفول افتراضياً
  SerialBT.begin("SmartLock_ESP32"); // اسم الجهاز الظاهر في البلوتوث
}

void loop() {
  if (SerialBT.available()) {
    char cmd = SerialBT.read();

    if (cmd == 'U') {
      digitalWrite(LOCK_PIN, HIGH); // فتح القفل
    }
    else if (cmd == 'L') {
      digitalWrite(LOCK_PIN, LOW); // قفل
    }
  }
}
