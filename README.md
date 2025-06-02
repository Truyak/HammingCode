# Hamming SEC-DED Kod Simülatörü

## Proje Amacı
- 8, 16 veya 32 bitlik ikili veriler üzerinde Hamming SEC-DED kodu hesaplar.
- Kullanıcı tarafından belirtilen bir bit pozisyonunda hata tanıtır.
- Sendrom ile hatayı tespit eder ve düzeltir.
- Sonuçları kullanıcı dostu bir arayüzde gösterir.

## Kullanılan Teknolojiler
- **Dil:** Java  
- **Arayüz:** JavaFX  

## Kullanım
1. Veri boyutunu seçin (8, 16, 32 bit).  
2. İkili veriyi girin (örneğin, `10011111`).  
3. Hata pozisyonunu belirtin (örneğin, `9`) veya boş bırakın.  
4. "Calculate Hamming Code" butonuna tıklayın.  
5. Sonuçları inceleyin: Hamming kodu, hatalı veri, sendrom ve düzeltilmiş kod.  

## Örnek
- **Giriş Verisi:** `10011111` (8 bit)  
- **Hata Pozisyonu:** `9`  
- **Hamming Kodu:** `001000101111`  
- **Hatalı Veri:** `001000011111`  
- **Sendrom:** `9 (Error at bit 9)`  
- **Düzeltilmiş Kod:** `001000101111`
