create table ADDRESS (ID bigint not null, VERSION_ID integer not null, UUID varchar(255), ZIPCODE varchar(255), STREET varchar(255), HOUSENR varchar(255), CITY varchar(255), COUNTRY varchar(255), GEOLOCATION_ID bigint, primary key (ID));
create table APPEARANCE (ID bigint not null, VERSION_ID integer not null, UUID varchar(255), HEIGHT integer, WEIGHT integer, BODYTYPE varchar(255), EYECOLOR varchar(255), HAIRCOLOR varchar(255), ETHNICITY varchar(255), primary key (ID));
create table APPEARANCE_APPEARANCESTYLES (APPEARANCE_ID bigint not null, APPEARANCESTYLE varchar(255));
create table BANKACCOUNT (ID bigint not null, BANKCOUNTRY varchar(255), BANKACCOUNT varchar(255), BANKCODE integer, BANKACCOUNTHOLDER varchar(255), primary key (ID));
create table BLOCKEDUSER (ID bigint not null, VERSION_ID integer not null, UUID varchar(255), TIME_STAMP timestamp, OWNER bigint, TARGET bigint, primary key (ID));
create table CONTACTEVENT (ID bigint not null, VERSION_ID integer not null, UUID varchar(255), TIMESTAMP timestamp, CONTACTEVENTTYPE varchar(255), USERACCOUNT_ID bigint, primary key (ID));
create table CREDITCARDACCOUNT (ID bigint not null, PSEUDOPAN varchar(255), HOLDERNAME varchar(255), TRUNCATED_PAN varchar(255), VALIDTHRU timestamp, primary key (ID));
create table FAVORITE (ID bigint not null, VERSION_ID integer not null, UUID varchar(255), TIME_STAMP timestamp, OWNER bigint, TARGET bigint, primary key (ID));
create table GEOAREA (ID bigint not null, VERSION_ID integer not null, UUID varchar(255), CONTINENT varchar(255), COUNTRY varchar(255), MAXDISTANCE varchar(255), ZIPCODE_START varchar(255), GEOLOCATION_ID bigint, SUBDIVISION_ID bigint, primary key (ID));
create table GEOLOCATION (ID bigint not null, VERSION_ID integer not null, UUID varchar(255), NAME varchar(255), ZIPCODE varchar(255), LATITUDE double, LONGITUDE double, POPULATION integer, COUNTRY varchar(255), CONTINENT varchar(255), SUBDIVISION_BIGGEST_ID bigint, SUBDIVISION_SMALLEST_ID bigint, primary key (ID));
create table IMAGEENTRY (ID bigint not null, VERSION_ID integer not null, UUID varchar(255), PROFILEIMAGE boolean, VISIBLE boolean, CLIENTFILENAME varchar(255), LASTMODIFIED timestamp, MEDIATYPE varchar(255), HEIGHT integer, WIDTH integer, REPOSITORYID varchar(255), USERPROFILE_ID bigint, idx integer, primary key (ID));
create table LANDINGPAGEACTION (ID bigint not null, VERSION_ID integer not null, UUID varchar(255), TOKEN varchar(255), ACTIONTYPE varchar(255), USERACCOUNT_ID bigint, primary key (ID));
create table LIFESTYLE (ID bigint not null, VERSION_ID integer not null, UUID varchar(255), DRINKTYPE varchar(255), SMOKETYPE varchar(255), OCCUPATIONTYPE varchar(255), PROFESSIONTYPE varchar(255), HOMETYPE varchar(255), LIVINGSITUATION varchar(255), PLAYINSTRUMENT boolean, SPORTSACTIVITYTYPE varchar(255), HOLIDAYPLANNINGTYPE varchar(255), FITNESSTYPE varchar(255), primary key (ID));
create table LIFESTYLE_FOODTYPES (LIFESTYLE_ID bigint not null, FOODTYPE varchar(255));
create table LIFESTYLE_HOBBYTYPES (LIFESTYLE_ID bigint not null, HOBBYTYPE varchar(255));
create table LIFESTYLE_HOLIDAYTYPES (LIFESTYLE_ID bigint not null, HOLIDAYTYPE varchar(255));
create table LIFESTYLE_KITCHENTYPES (LIFESTYLE_ID bigint not null, KITCHENTYPE varchar(255));
create table LIFESTYLE_MUSICTYPES (LIFESTYLE_ID bigint not null, MUSICTYPE varchar(255));
create table LIFESTYLE_PETTYPES (LIFESTYLE_ID bigint not null, PETTYPE varchar(255));
create table LIFESTYLE_SPORTTYPES (LIFESTYLE_ID bigint not null, SPORTTYPE varchar(255));
create table MEMBERSHIP (ID bigint not null, VERSION_ID integer not null, UUID varchar(255), USERACCOUNT_ID bigint, STARTDATE timestamp, ENDDATE timestamp, ACCEPTEDTERMS boolean, STATUS varchar(255), PAYMENTTRANSACTION bigint, PRODUCT bigint, PROMOTION bigint, primary key (ID));
create table MESSAGE (ID bigint not null, VERSION_ID integer not null, UUID varchar(255), MESSAGETYPE varchar(255), MESSAGESTATE_RECEIVER varchar(255), MESSAGESTATE_SENDER varchar(255), TIME_STAMP timestamp, SUBJECT varchar(255), TEXT varchar(4000), RECEIVER bigint, SENDER bigint, primary key (ID));
create table NOTIFICATIONSETTINGS (ID bigint not null, VERSION_ID integer not null, UUID varchar(255), ONNEWMESSAGE boolean, ONNEWSUGGESTIONS boolean, ONVISIT boolean, NEWSLETTER boolean, WEEKENDSUGGESTIONS boolean, WEEKLYSTATISTIC boolean, primary key (ID));
create table PAYMENTCHANNELS (ID bigint not null, VERSION_ID integer not null, UUID varchar(255), PAYMENTCHANNELTYPE varchar(255), USERACCOUNT_ID bigint, STARTDATE timestamp, ENDDATE timestamp, primary key (ID));
create table PAYONETRANSACTION (ID bigint not null, CUSTOMERMESSAGE varchar(255), ERRORCODE integer, ERRORMESSAGE varchar(255), MERCHANTTRANSACTIONREFERENCE varchar(255), PARAM varchar(255), PAYONEDEBITORID integer, PAYONEID bigint, REDIRECTURL bigint, SUBACCOUNTID integer, primary key (ID));
create table PERSONALVALUES (ID bigint not null, VERSION_ID integer not null, UUID varchar(255), SHOWINGEMOTIONS varchar(255), ROMANCE varchar(255), TENDERNESS varchar(255), LONGRELATIONSHIP varchar(255), SHORTRELATIONSHIP varchar(255), FREEDOM varchar(255), SEXUALITY varchar(255), DIFFERENTPARTNERS varchar(255), FAITHFULNESS varchar(255), RESPECT varchar(255), HONESTY varchar(255), SECURITY varchar(255), CONFIDENCE varchar(255), CHARM varchar(255), primary key (ID));
create table PRODUCT (ID bigint not null, VERSION_ID integer not null, UUID varchar(255), STARTDATE timestamp, ENDDATE timestamp, DURATION varchar(255), MONTHLYRATE float, ABO boolean, ROLE varchar(255), primary key (ID));
create table PROMOTION (ID bigint not null, VERSION_ID integer not null, UUID varchar(255), PROMOTION_TYPE varchar(255), CODE varchar(255), USED boolean, VALID_FROM timestamp, VALID_TO timestamp, TIME_STAMP timestamp, USERACCOUNT bigint, PRODUCT bigint, primary key (ID));
create table SEARCHPROFILE (ID bigint not null, VERSION_ID integer not null, UUID varchar(255), INCOMETYPE varchar(255), OCCUPATIONTYPE varchar(255), PARTNERTYPE varchar(255), PROFILE_PICTURE_TYPE varchar(255), MINAGE integer, MAXAGE integer, NUMBER_OF_KIDS_TYPE varchar(255), WANT_MORE_CHILDREN_TYPE varchar(255), PARTNERSHIPTYPE varchar(255), MOTHERLANGUAGE varchar(255), HOUSEHOLDTYPE varchar(255), MINHEIGHTCM integer, MAXHEIGHTCM integer, SMOKETYPE varchar(255), GEOAREA_ID bigint, primary key (ID));
create table SEARCHPROFILE_APPEARANCESTYLES (SEARCHPROFILE_ID bigint not null, APPEARANCESTYLE varchar(255));
create table SEARCHPROFILE_BODYTYPES (SEARCHPROFILE_ID bigint not null, BODYTYPE varchar(255));
create table SEARCHPROFILE_EDUCATIONTYPES (SEARCHPROFILE_ID bigint not null, EDUCATIONTYPE varchar(255));
create table SEARCHPROFILE_ETHNICITIES (SEARCHPROFILE_ID bigint not null, ETHNICITY varchar(255));
create table SEARCHPROFILE_EYECOLORS (SEARCHPROFILE_ID bigint not null, EYECOLOR varchar(255));
create table SEARCHPROFILE_FAMILYSTATUSTYPES (SEARCHPROFILE_ID bigint not null, FAMILYSTATUSTYPE varchar(255));
create table SEARCHPROFILE_HAIRCOLORS (SEARCHPROFILE_ID bigint not null, HAIRCOLOR varchar(255));
create table SEARCHPROFILE_HOBBYTYPES (SEARCHPROFILE_ID bigint not null, HOBBYTYPE varchar(255));
create table SEARCHPROFILE_LANGUAGES (SEARCHPROFILE_ID bigint not null, LANGUAGE varchar(255));
create table SEARCHPROFILE_RELIGIONS (SEARCHPROFILE_ID bigint not null, RELIGION varchar(255));
create table SEARCHPROFILE_SPORTTYPES (SEARCHPROFILE_ID bigint not null, SPORTTYPE varchar(255));
create table SUBDIVISION (ID bigint not null, VERSION_ID integer not null, UUID varchar(255), CODE varchar(255), NAME varchar(255), COUNTRY varchar(255), PARENT_ID bigint, primary key (ID));
create table TRANSACTION (ID bigint not null, VERSION_ID integer not null, UUID varchar(255), TIME_STAMP timestamp, CLEARINGTYPE varchar(255), STATUS varchar(255), AMOUNT integer, CURRENCY varchar(255), NARRATIVETEXT varchar(255), USERACCOUNT_ID bigint, PAYMENTCHANNEL bigint, primary key (ID));
create table USERACCOUNT (ID bigint not null, VERSION_ID integer not null, UUID varchar(255), ROLES varchar(255), LASTLOGIN timestamp, ONLINE boolean, PASSWORD varchar(255) not null, PASSWORD_SALT varchar(255), PREFERRED_LANGUAGE varchar(255), PREMIUM_END_DATE timestamp, USERNAME varchar(255), NOTIFICATIONSETTINGS bigint, USER_ID bigint, primary key (ID));
create table USERPROFILE (ID bigint not null, VERSION_ID integer not null, UUID varchar(255), BIRTHDATE timestamp, EDUCATION varchar(255), FAMILYSTATUS varchar(255), HOUSEHOLDTYPE varchar(255), INCOMETYPE varchar(255), MOTHERLANGUAGE varchar(255), MOTTO varchar(255), NUMBEROFKIDS varchar(255), OCCUPATIONTYPE varchar(255), PARTNERTYPE varchar(255), PROFESSION varchar(255), RELIGION varchar(255), WANT_MORE_CHILDREN_TYPE varchar(255), APPEARANCE bigint, PERSONALVALUES bigint, LIFESTYLE bigint, GEOLOCATION_ID bigint, primary key (ID));
create table USERPROFILE_LANGUAGES (USERPROFILE_ID bigint not null, LANGUAGE varchar(255));
create table USERS (ID bigint not null, VERSION_ID integer not null, UUID varchar(255), EMAIL varchar(256) not null, FIRSTNAME varchar(255), LASTNAME varchar(255), ADDRESS bigint, SEARCHPROFILE bigint, USERPROFILE bigint, USERACCOUNT bigint, primary key (ID));
create table VISIT (ID bigint not null, VERSION_ID integer not null, UUID varchar(255), TIME_STAMP timestamp, VISITEDUSER bigint, VISITOR bigint, primary key (ID));
create table VOUCHER_PAYMENT (ID bigint not null, PROMOTION_CODE varchar(255), primary key (ID));
create table WALLETACCOUNT (ID bigint not null, WALLETTYPE varchar(255), primary key (ID));
alter table ADDRESS add constraint FK_jfq632485tt6jcuu48bfuebq5 foreign key (GEOLOCATION_ID) references GEOLOCATION;
alter table APPEARANCE_APPEARANCESTYLES add constraint FK_nomxea5ox834uu6fv8sot3045 foreign key (APPEARANCE_ID) references APPEARANCE;
alter table BANKACCOUNT add constraint FK_op69frel3ilejkm1a1rprwu7e foreign key (ID) references PAYMENTCHANNELS;
alter table BLOCKEDUSER add constraint FK_7d5i3a5rg7t9higxae0jp8858 foreign key (OWNER) references USERS;
alter table BLOCKEDUSER add constraint FK_sdli12r9u83rlv1lok2b799ub foreign key (TARGET) references USERS;
alter table CONTACTEVENT add constraint FK_r3p5bhl2eivdepdpucg18k5vs foreign key (USERACCOUNT_ID) references USERACCOUNT;
alter table CREDITCARDACCOUNT add constraint FK_cwo4it88v8jefayd1qgh0d0bd foreign key (ID) references PAYMENTCHANNELS;
alter table FAVORITE add constraint FK_e551hxtqdoe4b8av11s3k7qfd foreign key (OWNER) references USERS;
alter table FAVORITE add constraint FK_9l7umh0qup2u5gk3qjnaoayaq foreign key (TARGET) references USERS;
alter table GEOAREA add constraint FK_cgxeynkpfkcfqi075a9uh9nr6 foreign key (GEOLOCATION_ID) references GEOLOCATION;
alter table GEOAREA add constraint FK_i0jfprbuffr3p48h2px5leb31 foreign key (SUBDIVISION_ID) references SUBDIVISION;
alter table GEOLOCATION add constraint FK_rm3scln3bn1pkv01xog1ty8ur foreign key (SUBDIVISION_BIGGEST_ID) references SUBDIVISION;
alter table GEOLOCATION add constraint FK_ru3a4q20gw25wmenr3ywcocii foreign key (SUBDIVISION_SMALLEST_ID) references SUBDIVISION;
alter table IMAGEENTRY add constraint FK_88whsprck03n7hnsuw6qhbuo4 foreign key (USERPROFILE_ID) references USERPROFILE;
alter table LANDINGPAGEACTION add constraint FK_2c7xktkm749hvplmwgei4kkbb foreign key (USERACCOUNT_ID) references USERACCOUNT;
alter table LIFESTYLE_FOODTYPES add constraint FK_qlfq2yvhsglxddsds6yxrhfng foreign key (LIFESTYLE_ID) references LIFESTYLE;
alter table LIFESTYLE_HOBBYTYPES add constraint FK_kv2v8c81nu9jneo3n3tntvjwb foreign key (LIFESTYLE_ID) references LIFESTYLE;
alter table LIFESTYLE_HOLIDAYTYPES add constraint FK_1oq1xlr3f9mfbt7qrsmby3m1a foreign key (LIFESTYLE_ID) references LIFESTYLE;
alter table LIFESTYLE_KITCHENTYPES add constraint FK_qjygvtl1agemcr7w0kan95hb4 foreign key (LIFESTYLE_ID) references LIFESTYLE;
alter table LIFESTYLE_MUSICTYPES add constraint FK_mcu4qwjiauy3svxgg7i9ip862 foreign key (LIFESTYLE_ID) references LIFESTYLE;
alter table LIFESTYLE_PETTYPES add constraint FK_3cc0twfn8xkv9r8bbe2fdc9pu foreign key (LIFESTYLE_ID) references LIFESTYLE;
alter table LIFESTYLE_SPORTTYPES add constraint FK_j6ocpo1mtugsx4ap59bho2yse foreign key (LIFESTYLE_ID) references LIFESTYLE;
alter table MEMBERSHIP add constraint UK_lc5psbpqoq0hd4uuf96br8aqn unique (USERACCOUNT_ID, STARTDATE);
alter table MEMBERSHIP add constraint FK_4o56pwwdmlyntk34hpbvxvenf foreign key (USERACCOUNT_ID) references USERACCOUNT;
alter table MEMBERSHIP add constraint FK_4htcm9h42a48nqa63gcd2nytu foreign key (PAYMENTTRANSACTION) references TRANSACTION;
alter table MEMBERSHIP add constraint FK_tjitp7itdr5yiwufwhsmc3gtu foreign key (PRODUCT) references PRODUCT;
alter table MEMBERSHIP add constraint FK_9uyr8by4ar4yppwbcvm09pjbw foreign key (PROMOTION) references PROMOTION;
alter table MESSAGE add constraint FK_d6nbvs0p4voveiy6wht8hw0bk foreign key (RECEIVER) references USERS;
alter table MESSAGE add constraint FK_h9p7rncwietw6etuf4a5g7ha foreign key (SENDER) references USERS;
alter table PAYMENTCHANNELS add constraint UK_92faeh2rco730xwreoltacuub unique (USERACCOUNT_ID, STARTDATE);
alter table PAYMENTCHANNELS add constraint FK_brvmbjgughmxv3m98thxyirq9 foreign key (USERACCOUNT_ID) references USERACCOUNT;
alter table PAYONETRANSACTION add constraint FK_m5nr166rod3j9wc0sc37pr9nj foreign key (ID) references TRANSACTION;
alter table PROMOTION add constraint FK_7jh1tdxa2xdclniqm8es5l8hp foreign key (USERACCOUNT) references USERACCOUNT;
alter table PROMOTION add constraint FK_13dlvwamof9h45801jujv0nty foreign key (PRODUCT) references PRODUCT;
alter table SEARCHPROFILE add constraint FK_13k398huetcjsbdxjrlaoabvb foreign key (GEOAREA_ID) references GEOAREA;
alter table SEARCHPROFILE_APPEARANCESTYLES add constraint FK_gnp46skacxtb5rwxqdt1cvkj8 foreign key (SEARCHPROFILE_ID) references SEARCHPROFILE;
alter table SEARCHPROFILE_BODYTYPES add constraint FK_lliws8rq4j3rch6cmsfcim253 foreign key (SEARCHPROFILE_ID) references SEARCHPROFILE;
alter table SEARCHPROFILE_EDUCATIONTYPES add constraint FK_lfok323h6pxrpmvk6n1illm00 foreign key (SEARCHPROFILE_ID) references SEARCHPROFILE;
alter table SEARCHPROFILE_ETHNICITIES add constraint FK_4398gdwq8d4ko99d0k6mdilfk foreign key (SEARCHPROFILE_ID) references SEARCHPROFILE;
alter table SEARCHPROFILE_EYECOLORS add constraint FK_rr0o9xy8m067lk7xhsl7gc8tp foreign key (SEARCHPROFILE_ID) references SEARCHPROFILE;
alter table SEARCHPROFILE_FAMILYSTATUSTYPES add constraint FK_9ahw80ih9m40m12rdcjyi0t6b foreign key (SEARCHPROFILE_ID) references SEARCHPROFILE;
alter table SEARCHPROFILE_HAIRCOLORS add constraint FK_71nm2rjvh9gv33s5eo2d1st64 foreign key (SEARCHPROFILE_ID) references SEARCHPROFILE;
alter table SEARCHPROFILE_HOBBYTYPES add constraint FK_lkx7kx954jhcya9pb3qn1lvfl foreign key (SEARCHPROFILE_ID) references SEARCHPROFILE;
alter table SEARCHPROFILE_LANGUAGES add constraint FK_dts6w44w9yk2k95g6m5nkdmf2 foreign key (SEARCHPROFILE_ID) references SEARCHPROFILE;
alter table SEARCHPROFILE_RELIGIONS add constraint FK_9wp8r4x9ugddb7fdwssa8t9e3 foreign key (SEARCHPROFILE_ID) references SEARCHPROFILE;
alter table SEARCHPROFILE_SPORTTYPES add constraint FK_1aws3wbqmg9i2j09dx2ptqkd6 foreign key (SEARCHPROFILE_ID) references SEARCHPROFILE;
alter table SUBDIVISION add constraint UK_1s6fa3dlr8e3g3wmjfngbcgb9 unique (CODE, NAME, COUNTRY);
alter table SUBDIVISION add constraint FK_jj7jsumqn5tmmp3oa61rxau59 foreign key (PARENT_ID) references SUBDIVISION;
alter table TRANSACTION add constraint FK_jh42tthkkrdy6uv7svbqxmes4 foreign key (USERACCOUNT_ID) references USERACCOUNT;
alter table TRANSACTION add constraint FK_kj5q4y6mxbwlboteqn70tme8a foreign key (PAYMENTCHANNEL) references PAYMENTCHANNELS;
alter table USERACCOUNT add constraint FK_1bb0f5g5hue9x9l8whhstjup5 foreign key (NOTIFICATIONSETTINGS) references NOTIFICATIONSETTINGS;
alter table USERACCOUNT add constraint FK_k0yr3ra96cf8uraic16vv0g2j foreign key (USER_ID) references USERS;
alter table USERPROFILE add constraint FK_m9dpmhwnyb63nu9sbspmm98k8 foreign key (APPEARANCE) references APPEARANCE;
alter table USERPROFILE add constraint FK_fmtgcmhvrojuydl157r3qeo0h foreign key (PERSONALVALUES) references PERSONALVALUES;
alter table USERPROFILE add constraint FK_25ke3uny2snunns9p0nsjn6kj foreign key (LIFESTYLE) references LIFESTYLE;
alter table USERPROFILE add constraint FK_a45h3p9vtdo0pvmpiug46fd8f foreign key (GEOLOCATION_ID) references GEOLOCATION;
alter table USERPROFILE_LANGUAGES add constraint FK_n1jjxjwv0gtv5hr9gigv4gv1c foreign key (USERPROFILE_ID) references USERPROFILE;
alter table USERS add constraint FK_lm4qp4jlhpbh4ixgn8bcs6f5k foreign key (ADDRESS) references ADDRESS;
alter table USERS add constraint FK_8m700ot48mgyrfyups3va1v7f foreign key (SEARCHPROFILE) references SEARCHPROFILE;
alter table USERS add constraint FK_kviu6736jke3nevnpyw8r01f6 foreign key (USERPROFILE) references USERPROFILE;
alter table USERS add constraint FK_78a2slhncdjsv8pq09etodq3q foreign key (USERACCOUNT) references USERACCOUNT;
alter table VISIT add constraint FK_9vfo5mkbb5a86f4rk6tqlky38 foreign key (VISITEDUSER) references USERS;
alter table VISIT add constraint FK_of7ced5rm7lnacbcd3wee8250 foreign key (VISITOR) references USERS;
alter table VOUCHER_PAYMENT add constraint FK_cdilxm4rgvi5v4e7sfkgxg4dp foreign key (ID) references PAYMENTCHANNELS;
alter table WALLETACCOUNT add constraint FK_ortx7vh2nwucgylatdsomdf09 foreign key (ID) references PAYMENTCHANNELS;
create table ID_SEQUENCES ( sequence_name varchar(255) not null ,  next_val bigint, primary key ( sequence_name ) );
INSERT INTO ID_SEQUENCES VALUES('com.pairoo.domain.User',1);
DELETE FROM ID_SEQUENCES WHERE SEQUENCE_NAME='com.pairoo.domain.User';
INSERT INTO ID_SEQUENCES VALUES('com.pairoo.domain.User',11);
COMMIT;
DELETE FROM ID_SEQUENCES WHERE SEQUENCE_NAME='com.pairoo.domain.User';
INSERT INTO ID_SEQUENCES VALUES('com.pairoo.domain.User',21);
COMMIT;
INSERT INTO ID_SEQUENCES VALUES('com.pairoo.domain.Address',1);
DELETE FROM ID_SEQUENCES WHERE SEQUENCE_NAME='com.pairoo.domain.Address';
INSERT INTO ID_SEQUENCES VALUES('com.pairoo.domain.Address',11);
COMMIT;
DELETE FROM ID_SEQUENCES WHERE SEQUENCE_NAME='com.pairoo.domain.Address';
INSERT INTO ID_SEQUENCES VALUES('com.pairoo.domain.Address',21);
COMMIT;
INSERT INTO ID_SEQUENCES VALUES('com.pairoo.domain.UserAccount',1);
DELETE FROM ID_SEQUENCES WHERE SEQUENCE_NAME='com.pairoo.domain.UserAccount';
INSERT INTO ID_SEQUENCES VALUES('com.pairoo.domain.UserAccount',11);
COMMIT;
DELETE FROM ID_SEQUENCES WHERE SEQUENCE_NAME='com.pairoo.domain.UserAccount';
INSERT INTO ID_SEQUENCES VALUES('com.pairoo.domain.UserAccount',21);
COMMIT;
INSERT INTO ID_SEQUENCES VALUES('com.pairoo.domain.NotificationSettings',1);
DELETE FROM ID_SEQUENCES WHERE SEQUENCE_NAME='com.pairoo.domain.NotificationSettings';
INSERT INTO ID_SEQUENCES VALUES('com.pairoo.domain.NotificationSettings',11);
COMMIT;
DELETE FROM ID_SEQUENCES WHERE SEQUENCE_NAME='com.pairoo.domain.NotificationSettings';
INSERT INTO ID_SEQUENCES VALUES('com.pairoo.domain.NotificationSettings',21);
COMMIT;