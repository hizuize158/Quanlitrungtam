
/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     24/06/2020 8:59:01 CH                        */
/*==============================================================*/


/*==============================================================*/
/* Table: NHAN_VIEN                                             */
/*==============================================================*/
create table NHAN_VIEN 
(
   MA_NV                NUMBER(38)           not null,
   TEN_NV               VARCHAR2(100)        not null,
   SDT                  VARCHAR2(20),
   LOAI                 NUMBER(1),
   TEN_DANG_NHAP        VARCHAR2(100),
   MAT_KHAU             VARCHAR2(200),
   SRC_IMG		VARCHAR2(200),
   constraint PK_NHAN_VIEN primary key (MA_NV)
);
--sequence
create sequence nhan_vien_sequence
increment by 1
start with 6000;

/*==============================================================*/
/* Table: CHUNG_CHI                                             */
/*==============================================================*/
create table CHUNG_CHI 
(
   MA_CHUNG_CHI         NUMBER(38)           not null,
   TEN_CHUNG_CHI        VARCHAR2(100)        not null,
   NOI_DUNG             VARCHAR2(1000),
   DIEM_TOI_DA		NUMBER(4,1),
   SRC_IMG              VARCHAR2(200),
   constraint PK_CHUNG_CHI primary key (MA_CHUNG_CHI)
);

--Sequenct: CHUNG_CHI
create sequence chung_chi_sequence
increment by 1
start with 100;

/*==============================================================*/
/* Table: CHUONG_TRINH                                          */
/*==============================================================*/
create table CHUONG_TRINH 
(
   MA_CT                NUMBER(38)           not null,
   MA_CHUNG_CHI         NUMBER(38)           not null,
   TEN_CT               VARCHAR2(100)        not null,
   DIEM_DAU_VAO         NUMBER(4,1)          not null,
   DIEM_DAU_RA          NUMBER(4,1)          not null,
   NOI_DUNG             VARCHAR2(1000),
   TRANG_THAI           NUMBER(1,0),
   TINH_DIEM_NGHE       NUMBER(1,0),
   TINH_DIEM_NOI       NUMBER(1,0),
   TINH_DIEM_DOC       NUMBER(1,0),
   TINH_DIEM_VIET       NUMBER(1,0),
   CACH_TINH_DIEM       NUMBER(1,0),
   constraint PK_CHUONG_TRINH primary key (MA_CT)
);

--sequence
create sequence chuong_trinh_sequence
increment by 1
start with 200

/*==============================================================*/
/* Index: THUOC_CHUNG_CHI_FK                                    */
/*==============================================================*/
create index THUOC_CHUNG_CHI_FK on CHUONG_TRINH (
   MA_CHUNG_CHI ASC
);

/*==============================================================*/
/* Table: KHACH_HANG                                            */
/*==============================================================*/
create table KHACH_HANG 
(
   MA_KH                NUMBER(38)           not null,
   TEN_KH               VARCHAR2(100)        not null,
   NGAY_SINH           DATE,
   GIOI_TINH            NUMBER(1),
   DIA_CHI              VARCHAR2(100),
   SDT                  VARCHAR2(20),
   DIEM_DAU_VAO         NUMBER(4,1),
   CHUNG_CHI_CAN_HOC    NUMBER(38),
   LOP_DANG_HOC         VARCHAR2(100),
   constraint PK_KHACH_HANG primary key (MA_KH)
);
--sequence
create sequence khach_hang_sequence
increment by 1
start with 5000

/*==============================================================*/
/* Table: LOP                                                   */
/*==============================================================*/
create table LOP 
(
   MA_LOP               NUMBER(38)           not null,
   MA_CT                NUMBER(38)           not null,
   MA_NV                NUMBER(38)           not null,
   TEN_LOP              VARCHAR2(100)        not null,
   NGAY_BD              DATE,
   NGAY_KT              DATE,
   SO_BUOI              NUMBER(4),
   TRANG_THAI           NUMBER(1),
   constraint PK_LOP primary key (MA_LOP)
);

--sequence
create sequence lop_sequence
increment by 1
start with 6000

/*==============================================================*/
/* Index: THUOC_FK                                              */
/*==============================================================*/
create index THUOC_FK on LOP (
   MA_CT ASC
);

/*==============================================================*/
/* Table: KQHT                                                  */
/*==============================================================*/
create table KQHT 
(
   MA_KH                NUMBER(38)           not null,
   MA_LOP               NUMBER(38)           not null,
   NGHE                 NUMBER(4,1),
   NOI                  NUMBER(4,1),
   DOC                  NUMBER(4,1),
   VIET                 NUMBER(4,1),
   TONG                 NUMBER(4,1), 
   constraint PK_KQHT primary key (MA_KH, MA_LOP)
);

/*==============================================================*/
/* Index: KQHT_FK                                               */
/*==============================================================*/
create index KQHT_FK on KQHT (
   MA_KH ASC
);

/*==============================================================*/
/* Index: KQHT2_FK                                              */
/*==============================================================*/
create index KQHT2_FK on KQHT (
   MA_LOP ASC
);



/*==============================================================*/
/* Index: PHU_TRACH_FK                                          */
/*==============================================================*/
create index PHU_TRACH_FK on LOP (
   MA_NV ASC
);

/*==============================================================*/
/* Table: GIAOVIEN                                              */
/*==============================================================*/
create table GIAO_VIEN 
(
   MA_GV                NUMBER(38)           not null,
   TEN_GV               VARCHAR2(100),
   GIOI_TINH            NUMBER(1),
   SDT                  VARCHAR2(20),
   QUOC_TICH            VARCHAR2(100),
   constraint PK_GIAOVIEN primary key (MA_GV)
);
--sequence
create sequence gv_sequence
increment by 1
start with 10000

/*==============================================================*/
/* Table: PHONG                                             */
/*==============================================================*/
create table PHONG 
(
   MA_PHONG             NUMBER(38)           not null,
   TEN_PHONG            VARCHAR2(100),
   constraint PK_PHONG primary key (MA_PHONG)
);

--sequence
create sequence phong_sequence
increment by 1
start with 8000

/*=============================================================*/
/* Table: LICH_NGAY                                             */
/*==============================================================*/
create table LICH_NGAY 
(
   MA_LOP               NUMBER(38)           not null,
   MA_GV                NUMBER(38)           not null,
   MA_PHONG             NUMBER(38)           not null,
   TG_BD               DATE,
   TG_KT               DATE,
   constraint PK_LICH_NGAY primary key (MA_LOP, MA_GV, MA_PHONG, TG_BD)
);

--index
create index LICH_NGAY_GV_FK on LICH_NGAY (
   MA_GV ASC
);

create index CO_LICH_NGAY_PHONG_FK on LICH_NGAY (
   MA_PHONG ASC
);

create index LICH_NGAY_FK on LICH_NGAY (
   MA_LOP ASC
);

/*==============================================================*/
/* FOREIGN KEY                                         */
/*==============================================================*/
alter table CHUONG_TRINH
   add constraint FK_CT_CC foreign key (MA_CHUNG_CHI)
      references CHUNG_CHI (MA_CHUNG_CHI);

alter table KQHT
   add constraint FK_KQHT_KH foreign key (MA_KH)
      references KHACH_HANG (MA_KH);

alter table KQHT
   add constraint FK_KQHT_LOP foreign key (MA_LOP)
      references LOP (MA_LOP);

alter table LOP
   add constraint FK_LOP_NV foreign key (MA_NV)
      references NHAN_VIEN (MA_NV);

alter table LOP
   add constraint FK_LOP_CT foreign key (MA_CT)
      references CHUONG_TRINH (MA_CT);

alter table LICH_NGAY
   add constraint FK_LICH_LOP foreign key (MA_LOP)
      references LOP (MA_LOP);

alter table LICH_NGAY
   add constraint FK_LICH_GV foreign key (MA_GV)
      references GIAO_VIEN (MA_GV);

alter table LICH_NGAY
   add constraint FK_LICH_PHONG foreign key (MA_PHONG)
      references PHONG (MA_PHONG);
/*==============================================================*/
/* TRIGGER: DELETE                                              */
/*==============================================================*/
--Trigger khi xóa Ch?ng ch? xóa ch??ng trình tr??c:
CREATE OR REPLACE TRIGGER TRIGGER_DELETE_CHUNG_CHI_DELETE_CHUONG_TRINH
	BEFORE DELETE
	ON CHUNG_CHI
	FOR EACH ROW
BEGIN
	DELETE FROM CHUONG_TRINH WHERE CHUONG_TRINH.MA_CHUNG_CHI = :OLD.MA_CHUNG_CHI;
END;

--Trigger khi xóa ch??ng trình xóa l?p tr??c:
CREATE OR REPLACE TRIGGER TRIGGER_DELETE_CHUONG_TRINH_DELETE_LOP
	BEFORE DELETE 
	ON CHUONG_TRINH
	FOR EACH ROW    
BEGIN
	DELETE FROM LOP WHERE LOP.MA_CT = :OLD.MA_CT;
END;

--Trigger khi xóa khách hàng thì xóa luôn k?t qu? h?c t?p 
CREATE OR REPLACE TRIGGER TRIGGER_DELETE_KHACH_HANG_DELETE_KQHT
	BEFORE DELETE 
	ON KHACH_HANG
	FOR EACH ROW    
BEGIN
	DELETE FROM KQHT WHERE KQHT.MA_KH = :OLD.MA_KH;
END;

--Trigger khi xóa l?p thì xóa luôn k?t qu? h?c t?p 
CREATE OR REPLACE TRIGGER TRIGGER_DELETE_LOP_DELETE_KQHT
	BEFORE DELETE 
	ON LOP
	FOR EACH ROW    
BEGIN
	DELETE FROM KQHT WHERE KQHT.MA_LOP = :OLD.MA_LOP;
END;

--Trigger khi xóa nhân viên xóa l?p tr??c:
CREATE OR REPLACE TRIGGER TRIGGER_DELETE_NHAN_VIEN_DELETE_LOP
	BEFORE DELETE 
	ON NHAN_VIEN
	FOR EACH ROW    
BEGIN
	DELETE FROM LOP WHERE LOP.MA_NV = :OLD.MA_NV;
END;

--Trigger khi xóa l?p thì xóa L?ch Ngày
CREATE OR REPLACE TRIGGER TRIGGER_DELETE_LOP_DELETE_LICH_NGAY
    BEFORE DELETE
    ON LOP
    FOR EACH ROW
BEGIN
    DELETE FROM LICH_NGAY WHERE LICH_NGAY.MA_LOP = :OLD.MA_LOP;
END;

--Trigger khi xóa GIAOS VIEN thì xóa L?ch Ngày
CREATE OR REPLACE TRIGGER TRIGGER_DELETE_GV_DELETE_LICH_NGAY
    BEFORE DELETE
    ON GIAO_VIEN
    FOR EACH ROW
BEGIN
    DELETE FROM LICH_NGAY WHERE LICH_NGAY.MA_GV = :OLD.MA_GV;
END;

--Trigger khi xóa PHÒNG thì xóa L?ch Ngày
CREATE OR REPLACE TRIGGER TRIGGER_DELETE_PHONG_DELETE_LICH_NGAY
    BEFORE DELETE
    ON PHONG
    FOR EACH ROW
BEGIN
    DELETE FROM LICH_NGAY WHERE LICH_NGAY.MA_PHONG = :OLD.MA_PHONG;
END;
