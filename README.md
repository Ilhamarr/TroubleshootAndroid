# TroubleshootAndroid
This is a mobile application of Troubeshoot.id.
for a startup computer service, it can be make client trust about this businness.

<hr>

## Developer Team
| No | NAMA           | NIM        | ROLE                |
|----|----------------|------------|---------------------|
| 1  | Vivi Rofiah    | 1313617001 | Front End Developer |
| 2  | Bagus Nugraha  | 1313617002 | Back End Developer  |
| 3  | Ilham Arrosyid | 1313617018 | Project Leader        |



## Product Backlog


### Story of Product

This Project is make mobile application of Troubleshoot.id. this app will give best experience for user to order the service easily. the step for make this application are, discuss about the business process of this app, make a design mockup in adobe XD (wireframe), Implement the design to layouting in android studio, Updating the database from website (already have a website), Make an API to connecting the database to application, Connecting all (layout, Controller, Database) same a business process that hve been discussed, Testing the application and Realease to user.

When we make this application, every sunday we have a meeting to discuss that all what a member to do in a week and give a task to do in a week forward.


#### SPRINT4:
this sprint, we focused on order flow, how to push the prooduct to cart, and give more information to ordering.
we usually discus about our project task in the first day of the week, below is the record of our meeting <br>
* [MEETING VIDEO - START SPRINT4](https://youtu.be/TZcxWRS6IcQ) 
* [MEETING VIDEO - END SPRINT4](https://youtu.be/NHC1xL2pDXM)

focus work sprint 4:
| Story         | TASK                                        | CONTRIBUTOR | STATUS   |   
|----------------------------------------------|---------------------------------------|-------------|----------|
| Mengisi Form Pesanan | Membuat layout konfirmasi pesanan                              | Ilham       | Complete |   
| Memilih Layanan service              | Membuat logical add to cart                                 | Bagus       | Complete |   
| Melanjutkan ke Proses Pembayaran              | Membuat Layout Konfirmasi Pembayaran                            | Vivi      | Complete |   
|               | Membuat Layout Metode Pembayaran                  | Vivi      | Complete   |    
| Melihat tampilan awal masuk kedalam aplikasi              | Revision Bottom Navigation                  | Ilham       | Complete |   
| Melihat Transaksi Pemesanan     | Membuat Layout Detail Order \(Bottom Sheet\) | Vivi        | Complete | 






all the detail of product backlocg are in this link below : <br>
https://docs.google.com/spreadsheets/d/1l3uG52OoAbAXDz2HFyEmODxLGKXyCd1aa_SbZJHBkXs/edit#gid=1386834576



## Mockup wireframe:
mockup mobile apps Troubleshoot.id:<br>
https://github.com/Ilhamarr/Troubleshoot_Design/blob/master/Documentation.md


## REST API
the component in API that we made: 

|   | GET                                          |
|---|----------------------------------------------|
| 1 | GET list merk Laptop                         |
| 2 | GET Merk Laptop by Id                        |
| 3 | GET list layanan troubleshoot                |
| 4 | GET Layanan troubleshoot by Id               |
| 5 | GET login (butuh parameter (email,password)) |
|   |                                              |
|   | POST                                         |
| 1 | POST register akun baru                      |
| 2 | POST melakukan pemesanan jasa troubleshoot   |

the source code of the API:
https://github.com/bagusn13/RestAPITroublehoot
