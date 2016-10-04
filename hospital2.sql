PGDMP     3                    t           hospital    9.5.4    9.5.4 �    	           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false            	           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false            	           1262    16393    hospital    DATABASE     �   CREATE DATABASE hospital WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Ukrainian_Ukraine.1251' LC_CTYPE = 'Ukrainian_Ukraine.1251';
    DROP DATABASE hospital;
             postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
             postgres    false            	           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                  postgres    false    7            	           0    0    public    ACL     �   REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;
                  postgres    false    7                        3079    12355    plpgsql 	   EXTENSION     ?   CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;
    DROP EXTENSION plpgsql;
                  false            	           0    0    EXTENSION plpgsql    COMMENT     @   COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';
                       false    1            �            1259    23110    appointment    TABLE     �   CREATE TABLE appointment (
    id bigint NOT NULL,
    end_date bytea,
    start_date bytea,
    text character varying(255),
    doctorinfo_id bigint,
    userdetail_id bigint
);
    DROP TABLE public.appointment;
       public         postgres    false    7            �            1259    23356    appointment_id_seq    SEQUENCE     t   CREATE SEQUENCE appointment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.appointment_id_seq;
       public       postgres    false    7            �            1259    23118    carditem    TABLE     �   CREATE TABLE carditem (
    id bigint NOT NULL,
    complaint text NOT NULL,
    date timestamp without time zone,
    prescription text NOT NULL,
    result text NOT NULL,
    doctor_id bigint,
    patientcard_id bigint
);
    DROP TABLE public.carditem;
       public         postgres    false    7            �            1259    23358    carditem_id_seq    SEQUENCE     q   CREATE SEQUENCE carditem_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.carditem_id_seq;
       public       postgres    false    7            �            1259    16410    databasechangelog    TABLE     )  CREATE TABLE databasechangelog (
    id character varying(255) NOT NULL,
    author character varying(255) NOT NULL,
    filename character varying(255) NOT NULL,
    dateexecuted timestamp without time zone NOT NULL,
    orderexecuted integer NOT NULL,
    exectype character varying(10) NOT NULL,
    md5sum character varying(35),
    description character varying(255),
    comments character varying(255),
    tag character varying(255),
    liquibase character varying(20),
    contexts character varying(255),
    labels character varying(255)
);
 %   DROP TABLE public.databasechangelog;
       public         postgres    false    7            �            1259    16416    databasechangeloglock    TABLE     �   CREATE TABLE databasechangeloglock (
    id integer NOT NULL,
    locked boolean NOT NULL,
    lockgranted timestamp without time zone,
    lockedby character varying(255)
);
 )   DROP TABLE public.databasechangeloglock;
       public         postgres    false    7            �            1259    23126 
   department    TABLE     �   CREATE TABLE department (
    id bigint NOT NULL,
    imagepath character varying(255),
    name character varying(255),
    hospital_id bigint
);
    DROP TABLE public.department;
       public         postgres    false    7            �            1259    23102    department_doctorinfo    TABLE     k   CREATE TABLE department_doctorinfo (
    departments_id bigint NOT NULL,
    doctors_id bigint NOT NULL
);
 )   DROP TABLE public.department_doctorinfo;
       public         postgres    false    7            �            1259    23360    department_id_seq    SEQUENCE     s   CREATE SEQUENCE department_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.department_id_seq;
       public       postgres    false    7            �            1259    23134 
   doctorinfo    TABLE     �   CREATE TABLE doctorinfo (
    id bigint NOT NULL,
    category character varying(255),
    specialization character varying(255),
    userdetails_id bigint
);
    DROP TABLE public.doctorinfo;
       public         postgres    false    7            �            1259    23362    doctorinfo_id_seq    SEQUENCE     s   CREATE SEQUENCE doctorinfo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.doctorinfo_id_seq;
       public       postgres    false    7            �            1259    23364    event_id_seq    SEQUENCE     n   CREATE SEQUENCE event_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.event_id_seq;
       public       postgres    false    7            �            1259    23142    events    TABLE       CREATE TABLE events (
    id bigint NOT NULL,
    end_date bytea,
    id_dhtmlx bigint,
    length bigint,
    pattern character varying(255),
    pid bigint,
    start_date bytea,
    text character varying(255),
    type character varying(255),
    scheduler_id bigint
);
    DROP TABLE public.events;
       public         postgres    false    7            �            1259    23150    feedback    TABLE     �   CREATE TABLE feedback (
    id bigint NOT NULL,
    date bytea,
    message character varying(255),
    consumer_id bigint,
    producer_id bigint
);
    DROP TABLE public.feedback;
       public         postgres    false    7            �            1259    23366    feedback_id_seq    SEQUENCE     q   CREATE SEQUENCE feedback_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.feedback_id_seq;
       public       postgres    false    7            �            1259    23158    hospital    TABLE     �  CREATE TABLE hospital (
    id bigint NOT NULL,
    building character varying(10),
    city character varying(30),
    country character varying(30),
    street character varying(30),
    description character varying(150) NOT NULL,
    imagepath character varying(255),
    latitude double precision NOT NULL,
    longitude double precision NOT NULL,
    name character varying(50) NOT NULL,
    CONSTRAINT hospital_latitude_check CHECK (((latitude <= (90)::double precision) AND (latitude >= ('-90'::integer)::double precision))),
    CONSTRAINT hospital_longitude_check CHECK (((longitude <= (180)::double precision) AND (longitude >= ('-180'::integer)::double precision)))
);
    DROP TABLE public.hospital;
       public         postgres    false    7            �            1259    23368    hospital_id_seq    SEQUENCE     q   CREATE SEQUENCE hospital_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.hospital_id_seq;
       public       postgres    false    7            �            1259    23168    hospital_users    TABLE     b   CREATE TABLE hospital_users (
    hospital_id bigint NOT NULL,
    managers_id bigint NOT NULL
);
 "   DROP TABLE public.hospital_users;
       public         postgres    false    7            �            1259    23370    password_reset_token_gen_id_seq    SEQUENCE     �   CREATE SEQUENCE password_reset_token_gen_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 6   DROP SEQUENCE public.password_reset_token_gen_id_seq;
       public       postgres    false    7            �            1259    23171    password_reset_tokens    TABLE     �   CREATE TABLE password_reset_tokens (
    id bigint NOT NULL,
    expirydate timestamp without time zone,
    token character varying(255),
    user_id bigint NOT NULL
);
 )   DROP TABLE public.password_reset_tokens;
       public         postgres    false    7            �            1259    23176    patientcard    TABLE     O   CREATE TABLE patientcard (
    id bigint NOT NULL,
    userdetail_id bigint
);
    DROP TABLE public.patientcard;
       public         postgres    false    7            �            1259    23372    patientcard_id_seq    SEQUENCE     t   CREATE SEQUENCE patientcard_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.patientcard_id_seq;
       public       postgres    false    7            �            1259    23181    patientinfo    TABLE     X   CREATE TABLE patientinfo (
    id bigint NOT NULL,
    cardid character varying(255)
);
    DROP TABLE public.patientinfo;
       public         postgres    false    7            �            1259    23374    patientinfo_id_seq    SEQUENCE     t   CREATE SEQUENCE patientinfo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.patientinfo_id_seq;
       public       postgres    false    7            �            1259    23186    persistent_logins    TABLE     �   CREATE TABLE persistent_logins (
    series character varying(255) NOT NULL,
    last_used timestamp without time zone,
    token character varying(255) NOT NULL,
    username character varying(255) NOT NULL
);
 %   DROP TABLE public.persistent_logins;
       public         postgres    false    7            �            1259    23194    role    TABLE     X   CREATE TABLE role (
    id bigint NOT NULL,
    type character varying(255) NOT NULL
);
    DROP TABLE public.role;
       public         postgres    false    7            �            1259    23376    role_id_seq    SEQUENCE     m   CREATE SEQUENCE role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.role_id_seq;
       public       postgres    false    7            �            1259    23105 
   role_users    TABLE     W   CREATE TABLE role_users (
    users_id bigint NOT NULL,
    role_id bigint NOT NULL
);
    DROP TABLE public.role_users;
       public         postgres    false    7            �            1259    23199 	   scheduler    TABLE     �   CREATE TABLE scheduler (
    id bigint NOT NULL,
    app_size smallint,
    day_end smallint,
    day_start smallint,
    week_size smallint,
    doctorinfo_id bigint
);
    DROP TABLE public.scheduler;
       public         postgres    false    7            �            1259    23378    scheduler_id_seq    SEQUENCE     r   CREATE SEQUENCE scheduler_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.scheduler_id_seq;
       public       postgres    false    7            �            1259    23204 
   userdetail    TABLE     R  CREATE TABLE userdetail (
    id bigint NOT NULL,
    address character varying(255),
    birthdate bytea,
    firstname character varying(255),
    gender character varying(255),
    imagepath character varying(255),
    lastname character varying(255),
    phone character varying(255),
    patientcard_id bigint,
    user_id bigint
);
    DROP TABLE public.userdetail;
       public         postgres    false    7            �            1259    23380    userdetail_id_seq    SEQUENCE     s   CREATE SEQUENCE userdetail_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.userdetail_id_seq;
       public       postgres    false    7            �            1259    23212    users    TABLE     �   CREATE TABLE users (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    enabled boolean NOT NULL,
    password character varying(255) NOT NULL,
    userdetails_id bigint
);
    DROP TABLE public.users;
       public         postgres    false    7            �            1259    23382    users_id_seq    SEQUENCE     n   CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.users_id_seq;
       public       postgres    false    7            �            1259    23384    verification_token_gen_id_seq    SEQUENCE        CREATE SEQUENCE verification_token_gen_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 4   DROP SEQUENCE public.verification_token_gen_id_seq;
       public       postgres    false    7            �            1259    23220    verifications_tokens    TABLE     �   CREATE TABLE verifications_tokens (
    id bigint NOT NULL,
    expirydate timestamp without time zone,
    token character varying(255),
    user_id bigint NOT NULL
);
 (   DROP TABLE public.verifications_tokens;
       public         postgres    false    7            �            1259    23225    workscheduler    TABLE     i   CREATE TABLE workscheduler (
    id bigint NOT NULL,
    workscheduler text,
    doctorinfo_id bigint
);
 !   DROP TABLE public.workscheduler;
       public         postgres    false    7            �          0    23110    appointment 
   TABLE DATA               \   COPY appointment (id, end_date, start_date, text, doctorinfo_id, userdetail_id) FROM stdin;
    public       postgres    false    185   _�       	           0    0    appointment_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('appointment_id_seq', 1, false);
            public       postgres    false    203            �          0    23118    carditem 
   TABLE DATA               a   COPY carditem (id, complaint, date, prescription, result, doctor_id, patientcard_id) FROM stdin;
    public       postgres    false    186   |�        	           0    0    carditem_id_seq    SEQUENCE SET     7   SELECT pg_catalog.setval('carditem_id_seq', 1, false);
            public       postgres    false    204            �          0    16410    databasechangelog 
   TABLE DATA               �   COPY databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels) FROM stdin;
    public       postgres    false    181   ��       �          0    16416    databasechangeloglock 
   TABLE DATA               K   COPY databasechangeloglock (id, locked, lockgranted, lockedby) FROM stdin;
    public       postgres    false    182   ��       �          0    23126 
   department 
   TABLE DATA               ?   COPY department (id, imagepath, name, hospital_id) FROM stdin;
    public       postgres    false    187   Ԡ       �          0    23102    department_doctorinfo 
   TABLE DATA               D   COPY department_doctorinfo (departments_id, doctors_id) FROM stdin;
    public       postgres    false    183   �       !	           0    0    department_id_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('department_id_seq', 1, false);
            public       postgres    false    205            �          0    23134 
   doctorinfo 
   TABLE DATA               K   COPY doctorinfo (id, category, specialization, userdetails_id) FROM stdin;
    public       postgres    false    188   %�       "	           0    0    doctorinfo_id_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('doctorinfo_id_seq', 1, false);
            public       postgres    false    206            #	           0    0    event_id_seq    SEQUENCE SET     4   SELECT pg_catalog.setval('event_id_seq', 1, false);
            public       postgres    false    207            �          0    23142    events 
   TABLE DATA               n   COPY events (id, end_date, id_dhtmlx, length, pattern, pid, start_date, text, type, scheduler_id) FROM stdin;
    public       postgres    false    189   V�       �          0    23150    feedback 
   TABLE DATA               H   COPY feedback (id, date, message, consumer_id, producer_id) FROM stdin;
    public       postgres    false    190   s�       $	           0    0    feedback_id_seq    SEQUENCE SET     7   SELECT pg_catalog.setval('feedback_id_seq', 1, false);
            public       postgres    false    208            �          0    23158    hospital 
   TABLE DATA               s   COPY hospital (id, building, city, country, street, description, imagepath, latitude, longitude, name) FROM stdin;
    public       postgres    false    191   ��       %	           0    0    hospital_id_seq    SEQUENCE SET     6   SELECT pg_catalog.setval('hospital_id_seq', 7, true);
            public       postgres    false    209            �          0    23168    hospital_users 
   TABLE DATA               ;   COPY hospital_users (hospital_id, managers_id) FROM stdin;
    public       postgres    false    192   ��       &	           0    0    password_reset_token_gen_id_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('password_reset_token_gen_id_seq', 1, false);
            public       postgres    false    210            �          0    23171    password_reset_tokens 
   TABLE DATA               H   COPY password_reset_tokens (id, expirydate, token, user_id) FROM stdin;
    public       postgres    false    193   ��       �          0    23176    patientcard 
   TABLE DATA               1   COPY patientcard (id, userdetail_id) FROM stdin;
    public       postgres    false    194   ã       '	           0    0    patientcard_id_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('patientcard_id_seq', 9, true);
            public       postgres    false    211             	          0    23181    patientinfo 
   TABLE DATA               *   COPY patientinfo (id, cardid) FROM stdin;
    public       postgres    false    195   ��       (	           0    0    patientinfo_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('patientinfo_id_seq', 1, false);
            public       postgres    false    212            	          0    23186    persistent_logins 
   TABLE DATA               H   COPY persistent_logins (series, last_used, token, username) FROM stdin;
    public       postgres    false    196   �       	          0    23194    role 
   TABLE DATA               !   COPY role (id, type) FROM stdin;
    public       postgres    false    197   1�       )	           0    0    role_id_seq    SEQUENCE SET     3   SELECT pg_catalog.setval('role_id_seq', 1, false);
            public       postgres    false    213            �          0    23105 
   role_users 
   TABLE DATA               0   COPY role_users (users_id, role_id) FROM stdin;
    public       postgres    false    184   s�       	          0    23199 	   scheduler 
   TABLE DATA               X   COPY scheduler (id, app_size, day_end, day_start, week_size, doctorinfo_id) FROM stdin;
    public       postgres    false    198   ��       *	           0    0    scheduler_id_seq    SEQUENCE SET     8   SELECT pg_catalog.setval('scheduler_id_seq', 1, false);
            public       postgres    false    214            	          0    23204 
   userdetail 
   TABLE DATA               }   COPY userdetail (id, address, birthdate, firstname, gender, imagepath, lastname, phone, patientcard_id, user_id) FROM stdin;
    public       postgres    false    199   Ȥ       +	           0    0    userdetail_id_seq    SEQUENCE SET     8   SELECT pg_catalog.setval('userdetail_id_seq', 9, true);
            public       postgres    false    215            	          0    23212    users 
   TABLE DATA               F   COPY users (id, email, enabled, password, userdetails_id) FROM stdin;
    public       postgres    false    200   ��       ,	           0    0    users_id_seq    SEQUENCE SET     3   SELECT pg_catalog.setval('users_id_seq', 9, true);
            public       postgres    false    216            -	           0    0    verification_token_gen_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('verification_token_gen_id_seq', 1, false);
            public       postgres    false    217            	          0    23220    verifications_tokens 
   TABLE DATA               G   COPY verifications_tokens (id, expirydate, token, user_id) FROM stdin;
    public       postgres    false    201   ��       	          0    23225    workscheduler 
   TABLE DATA               B   COPY workscheduler (id, workscheduler, doctorinfo_id) FROM stdin;
    public       postgres    false    202   ɧ       @           2606    23117    appointment_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY appointment
    ADD CONSTRAINT appointment_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.appointment DROP CONSTRAINT appointment_pkey;
       public         postgres    false    185    185            B           2606    23125    carditem_pkey 
   CONSTRAINT     M   ALTER TABLE ONLY carditem
    ADD CONSTRAINT carditem_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.carditem DROP CONSTRAINT carditem_pkey;
       public         postgres    false    186    186            D           2606    23133    department_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY department
    ADD CONSTRAINT department_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.department DROP CONSTRAINT department_pkey;
       public         postgres    false    187    187            F           2606    23141    doctorinfo_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY doctorinfo
    ADD CONSTRAINT doctorinfo_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.doctorinfo DROP CONSTRAINT doctorinfo_pkey;
       public         postgres    false    188    188            H           2606    23149    events_pkey 
   CONSTRAINT     I   ALTER TABLE ONLY events
    ADD CONSTRAINT events_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.events DROP CONSTRAINT events_pkey;
       public         postgres    false    189    189            J           2606    23157    feedback_pkey 
   CONSTRAINT     M   ALTER TABLE ONLY feedback
    ADD CONSTRAINT feedback_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.feedback DROP CONSTRAINT feedback_pkey;
       public         postgres    false    190    190            L           2606    23167    hospital_pkey 
   CONSTRAINT     M   ALTER TABLE ONLY hospital
    ADD CONSTRAINT hospital_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.hospital DROP CONSTRAINT hospital_pkey;
       public         postgres    false    191    191            N           2606    23175    password_reset_tokens_pkey 
   CONSTRAINT     g   ALTER TABLE ONLY password_reset_tokens
    ADD CONSTRAINT password_reset_tokens_pkey PRIMARY KEY (id);
 Z   ALTER TABLE ONLY public.password_reset_tokens DROP CONSTRAINT password_reset_tokens_pkey;
       public         postgres    false    193    193            P           2606    23180    patientcard_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY patientcard
    ADD CONSTRAINT patientcard_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.patientcard DROP CONSTRAINT patientcard_pkey;
       public         postgres    false    194    194            R           2606    23185    patientinfo_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY patientinfo
    ADD CONSTRAINT patientinfo_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.patientinfo DROP CONSTRAINT patientinfo_pkey;
       public         postgres    false    195    195            T           2606    23193    persistent_logins_pkey 
   CONSTRAINT     c   ALTER TABLE ONLY persistent_logins
    ADD CONSTRAINT persistent_logins_pkey PRIMARY KEY (series);
 R   ALTER TABLE ONLY public.persistent_logins DROP CONSTRAINT persistent_logins_pkey;
       public         postgres    false    196    196            <           2606    16533    pk_databasechangeloglock 
   CONSTRAINT     e   ALTER TABLE ONLY databasechangeloglock
    ADD CONSTRAINT pk_databasechangeloglock PRIMARY KEY (id);
 X   ALTER TABLE ONLY public.databasechangeloglock DROP CONSTRAINT pk_databasechangeloglock;
       public         postgres    false    182    182            Z           2606    23198 	   role_pkey 
   CONSTRAINT     E   ALTER TABLE ONLY role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.role DROP CONSTRAINT role_pkey;
       public         postgres    false    197    197            >           2606    23109    role_users_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY role_users
    ADD CONSTRAINT role_users_pkey PRIMARY KEY (users_id, role_id);
 D   ALTER TABLE ONLY public.role_users DROP CONSTRAINT role_users_pkey;
       public         postgres    false    184    184    184            ^           2606    23203    scheduler_pkey 
   CONSTRAINT     O   ALTER TABLE ONLY scheduler
    ADD CONSTRAINT scheduler_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.scheduler DROP CONSTRAINT scheduler_pkey;
       public         postgres    false    198    198            b           2606    23240    uk_6dotkott2kjsp8vw4d0m25fb7 
   CONSTRAINT     W   ALTER TABLE ONLY users
    ADD CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);
 L   ALTER TABLE ONLY public.users DROP CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7;
       public         postgres    false    200    200            \           2606    23238    uk_93vn3jeavtylq20tjdx2p2kkd 
   CONSTRAINT     U   ALTER TABLE ONLY role
    ADD CONSTRAINT uk_93vn3jeavtylq20tjdx2p2kkd UNIQUE (type);
 K   ALTER TABLE ONLY public.role DROP CONSTRAINT uk_93vn3jeavtylq20tjdx2p2kkd;
       public         postgres    false    197    197            V           2606    23234    uk_bqa9l0go97v49wwyx4c0u3kpd 
   CONSTRAINT     c   ALTER TABLE ONLY persistent_logins
    ADD CONSTRAINT uk_bqa9l0go97v49wwyx4c0u3kpd UNIQUE (token);
 X   ALTER TABLE ONLY public.persistent_logins DROP CONSTRAINT uk_bqa9l0go97v49wwyx4c0u3kpd;
       public         postgres    false    196    196            X           2606    23236    uk_f8t9fsfwc17s6qcbx0ath6l3h 
   CONSTRAINT     f   ALTER TABLE ONLY persistent_logins
    ADD CONSTRAINT uk_f8t9fsfwc17s6qcbx0ath6l3h UNIQUE (username);
 X   ALTER TABLE ONLY public.persistent_logins DROP CONSTRAINT uk_f8t9fsfwc17s6qcbx0ath6l3h;
       public         postgres    false    196    196            `           2606    23211    userdetail_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY userdetail
    ADD CONSTRAINT userdetail_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.userdetail DROP CONSTRAINT userdetail_pkey;
       public         postgres    false    199    199            d           2606    23219 
   users_pkey 
   CONSTRAINT     G   ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public         postgres    false    200    200            f           2606    23224    verifications_tokens_pkey 
   CONSTRAINT     e   ALTER TABLE ONLY verifications_tokens
    ADD CONSTRAINT verifications_tokens_pkey PRIMARY KEY (id);
 X   ALTER TABLE ONLY public.verifications_tokens DROP CONSTRAINT verifications_tokens_pkey;
       public         postgres    false    201    201            h           2606    23232    workscheduler_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY workscheduler
    ADD CONSTRAINT workscheduler_pkey PRIMARY KEY (id);
 J   ALTER TABLE ONLY public.workscheduler DROP CONSTRAINT workscheduler_pkey;
       public         postgres    false    202    202            r           2606    23286    fk_1r6dadb3ajbaxwkpdx1v4u9n9    FK CONSTRAINT     �   ALTER TABLE ONLY doctorinfo
    ADD CONSTRAINT fk_1r6dadb3ajbaxwkpdx1v4u9n9 FOREIGN KEY (userdetails_id) REFERENCES userdetail(id);
 Q   ALTER TABLE ONLY public.doctorinfo DROP CONSTRAINT fk_1r6dadb3ajbaxwkpdx1v4u9n9;
       public       postgres    false    188    2144    199            p           2606    23276    fk_23f7e1f3aoynlynkmrk6elwaq    FK CONSTRAINT     �   ALTER TABLE ONLY carditem
    ADD CONSTRAINT fk_23f7e1f3aoynlynkmrk6elwaq FOREIGN KEY (patientcard_id) REFERENCES patientcard(id);
 O   ALTER TABLE ONLY public.carditem DROP CONSTRAINT fk_23f7e1f3aoynlynkmrk6elwaq;
       public       postgres    false    2128    186    194            }           2606    23341    fk_2xe4lqrkwymg4aoshgadolask    FK CONSTRAINT        ALTER TABLE ONLY users
    ADD CONSTRAINT fk_2xe4lqrkwymg4aoshgadolask FOREIGN KEY (userdetails_id) REFERENCES userdetail(id);
 L   ALTER TABLE ONLY public.users DROP CONSTRAINT fk_2xe4lqrkwymg4aoshgadolask;
       public       postgres    false    200    199    2144            k           2606    23251    fk_67w5hj99v5nvjexfv0cf1mxus    FK CONSTRAINT     w   ALTER TABLE ONLY role_users
    ADD CONSTRAINT fk_67w5hj99v5nvjexfv0cf1mxus FOREIGN KEY (role_id) REFERENCES role(id);
 Q   ALTER TABLE ONLY public.role_users DROP CONSTRAINT fk_67w5hj99v5nvjexfv0cf1mxus;
       public       postgres    false    184    197    2138            ~           2606    23346    fk_79hy0bqeu0oobh0thq8bgbae1    FK CONSTRAINT     �   ALTER TABLE ONLY verifications_tokens
    ADD CONSTRAINT fk_79hy0bqeu0oobh0thq8bgbae1 FOREIGN KEY (user_id) REFERENCES users(id);
 [   ALTER TABLE ONLY public.verifications_tokens DROP CONSTRAINT fk_79hy0bqeu0oobh0thq8bgbae1;
       public       postgres    false    201    200    2148            t           2606    23296    fk_7uda74mreyd8pe0bc0rgm1ufe    FK CONSTRAINT     z   ALTER TABLE ONLY feedback
    ADD CONSTRAINT fk_7uda74mreyd8pe0bc0rgm1ufe FOREIGN KEY (consumer_id) REFERENCES users(id);
 O   ALTER TABLE ONLY public.feedback DROP CONSTRAINT fk_7uda74mreyd8pe0bc0rgm1ufe;
       public       postgres    false    2148    200    190            |           2606    23336    fk_90967b34b8g7o7uyt8uujn4ya    FK CONSTRAINT     x   ALTER TABLE ONLY userdetail
    ADD CONSTRAINT fk_90967b34b8g7o7uyt8uujn4ya FOREIGN KEY (user_id) REFERENCES users(id);
 Q   ALTER TABLE ONLY public.userdetail DROP CONSTRAINT fk_90967b34b8g7o7uyt8uujn4ya;
       public       postgres    false    2148    199    200                       2606    23351    fk_a5ufm3i0ew6t3nfua44q1mqvw    FK CONSTRAINT     �   ALTER TABLE ONLY workscheduler
    ADD CONSTRAINT fk_a5ufm3i0ew6t3nfua44q1mqvw FOREIGN KEY (doctorinfo_id) REFERENCES doctorinfo(id);
 T   ALTER TABLE ONLY public.workscheduler DROP CONSTRAINT fk_a5ufm3i0ew6t3nfua44q1mqvw;
       public       postgres    false    202    188    2118            n           2606    23266    fk_b51bk1hxlblc0fq7l65dkcnc5    FK CONSTRAINT     �   ALTER TABLE ONLY appointment
    ADD CONSTRAINT fk_b51bk1hxlblc0fq7l65dkcnc5 FOREIGN KEY (userdetail_id) REFERENCES userdetail(id);
 R   ALTER TABLE ONLY public.appointment DROP CONSTRAINT fk_b51bk1hxlblc0fq7l65dkcnc5;
       public       postgres    false    185    199    2144            l           2606    23256    fk_cunrd1kjdr3pwcekkhw8r5k2l    FK CONSTRAINT     y   ALTER TABLE ONLY role_users
    ADD CONSTRAINT fk_cunrd1kjdr3pwcekkhw8r5k2l FOREIGN KEY (users_id) REFERENCES users(id);
 Q   ALTER TABLE ONLY public.role_users DROP CONSTRAINT fk_cunrd1kjdr3pwcekkhw8r5k2l;
       public       postgres    false    184    200    2148            {           2606    23331    fk_exlldeucxpa91uwds0hpc97o    FK CONSTRAINT     �   ALTER TABLE ONLY userdetail
    ADD CONSTRAINT fk_exlldeucxpa91uwds0hpc97o FOREIGN KEY (patientcard_id) REFERENCES patientcard(id);
 P   ALTER TABLE ONLY public.userdetail DROP CONSTRAINT fk_exlldeucxpa91uwds0hpc97o;
       public       postgres    false    199    194    2128            s           2606    23291    fk_h25bu5arkbwfh9fwpabk0x9e2    FK CONSTRAINT     }   ALTER TABLE ONLY events
    ADD CONSTRAINT fk_h25bu5arkbwfh9fwpabk0x9e2 FOREIGN KEY (scheduler_id) REFERENCES scheduler(id);
 M   ALTER TABLE ONLY public.events DROP CONSTRAINT fk_h25bu5arkbwfh9fwpabk0x9e2;
       public       postgres    false    189    2142    198            m           2606    23261    fk_hx479pw4gje0ktsjfwvl9pwn8    FK CONSTRAINT     �   ALTER TABLE ONLY appointment
    ADD CONSTRAINT fk_hx479pw4gje0ktsjfwvl9pwn8 FOREIGN KEY (doctorinfo_id) REFERENCES doctorinfo(id);
 R   ALTER TABLE ONLY public.appointment DROP CONSTRAINT fk_hx479pw4gje0ktsjfwvl9pwn8;
       public       postgres    false    185    188    2118            z           2606    23326    fk_io94dc8e3xgt37ggaiq0kcsl1    FK CONSTRAINT     �   ALTER TABLE ONLY scheduler
    ADD CONSTRAINT fk_io94dc8e3xgt37ggaiq0kcsl1 FOREIGN KEY (doctorinfo_id) REFERENCES doctorinfo(id);
 P   ALTER TABLE ONLY public.scheduler DROP CONSTRAINT fk_io94dc8e3xgt37ggaiq0kcsl1;
       public       postgres    false    198    188    2118            i           2606    23241    fk_ivkij0rfsc9yoc3v5iamgux7c    FK CONSTRAINT     �   ALTER TABLE ONLY department_doctorinfo
    ADD CONSTRAINT fk_ivkij0rfsc9yoc3v5iamgux7c FOREIGN KEY (doctors_id) REFERENCES doctorinfo(id);
 \   ALTER TABLE ONLY public.department_doctorinfo DROP CONSTRAINT fk_ivkij0rfsc9yoc3v5iamgux7c;
       public       postgres    false    183    188    2118            j           2606    23246    fk_jdh6lxu9mgpemg0xeqqccwpdc    FK CONSTRAINT     �   ALTER TABLE ONLY department_doctorinfo
    ADD CONSTRAINT fk_jdh6lxu9mgpemg0xeqqccwpdc FOREIGN KEY (departments_id) REFERENCES department(id);
 \   ALTER TABLE ONLY public.department_doctorinfo DROP CONSTRAINT fk_jdh6lxu9mgpemg0xeqqccwpdc;
       public       postgres    false    183    187    2116            x           2606    23316    fk_la2ts67g4oh2sreayswhox1i6    FK CONSTRAINT     �   ALTER TABLE ONLY password_reset_tokens
    ADD CONSTRAINT fk_la2ts67g4oh2sreayswhox1i6 FOREIGN KEY (user_id) REFERENCES users(id);
 \   ALTER TABLE ONLY public.password_reset_tokens DROP CONSTRAINT fk_la2ts67g4oh2sreayswhox1i6;
       public       postgres    false    193    200    2148            v           2606    23306    fk_o7prsdub8c63s5w469e06q83    FK CONSTRAINT        ALTER TABLE ONLY hospital_users
    ADD CONSTRAINT fk_o7prsdub8c63s5w469e06q83 FOREIGN KEY (managers_id) REFERENCES users(id);
 T   ALTER TABLE ONLY public.hospital_users DROP CONSTRAINT fk_o7prsdub8c63s5w469e06q83;
       public       postgres    false    200    2148    192            o           2606    23271    fk_ohvlr9w2h9pvsj2bg12ox9b9    FK CONSTRAINT     w   ALTER TABLE ONLY carditem
    ADD CONSTRAINT fk_ohvlr9w2h9pvsj2bg12ox9b9 FOREIGN KEY (doctor_id) REFERENCES users(id);
 N   ALTER TABLE ONLY public.carditem DROP CONSTRAINT fk_ohvlr9w2h9pvsj2bg12ox9b9;
       public       postgres    false    186    2148    200            y           2606    23321    fk_ov506nbhqxddqios9pxlb21ie    FK CONSTRAINT     �   ALTER TABLE ONLY patientcard
    ADD CONSTRAINT fk_ov506nbhqxddqios9pxlb21ie FOREIGN KEY (userdetail_id) REFERENCES userdetail(id);
 R   ALTER TABLE ONLY public.patientcard DROP CONSTRAINT fk_ov506nbhqxddqios9pxlb21ie;
       public       postgres    false    194    199    2144            u           2606    23301    fk_pvf13utfatebavsixvqxwo3ob    FK CONSTRAINT     z   ALTER TABLE ONLY feedback
    ADD CONSTRAINT fk_pvf13utfatebavsixvqxwo3ob FOREIGN KEY (producer_id) REFERENCES users(id);
 O   ALTER TABLE ONLY public.feedback DROP CONSTRAINT fk_pvf13utfatebavsixvqxwo3ob;
       public       postgres    false    190    2148    200            q           2606    23281    fk_spotjwwrelb9dhulkiivbl62b    FK CONSTRAINT        ALTER TABLE ONLY department
    ADD CONSTRAINT fk_spotjwwrelb9dhulkiivbl62b FOREIGN KEY (hospital_id) REFERENCES hospital(id);
 Q   ALTER TABLE ONLY public.department DROP CONSTRAINT fk_spotjwwrelb9dhulkiivbl62b;
       public       postgres    false    187    2124    191            w           2606    23311    fk_tcu91xpek54a086jamuakvoh8    FK CONSTRAINT     �   ALTER TABLE ONLY hospital_users
    ADD CONSTRAINT fk_tcu91xpek54a086jamuakvoh8 FOREIGN KEY (hospital_id) REFERENCES hospital(id);
 U   ALTER TABLE ONLY public.hospital_users DROP CONSTRAINT fk_tcu91xpek54a086jamuakvoh8;
       public       postgres    false    192    2124    191            �      x������ � �      �      x������ � �      �     x���K��v���_������IM}}'	� �2�d�ۈ�$ѿ��q����:(H�=��X���]$i�ʒ���?��������v|�����v�w���o��_o�=���s����/��v��ǷO������������Dz#�)�T2t���?���_~��aoF��G0�-��?`nMd&��x��}���5~�c|h�8~���'}�����m�|k�%������Rr�H#Ƭ�9K�m�9V���h�z�=+��q�ҺH�FE�f��9��c��XK(�U�YgQ��V���Rdd/�;=��c*9q��b��J#�$p�.��8�h�9�t��bM������GrO\����jn!s�X�:�(%�a���k�V�p�B�{���LJcZ?ǚ�cMa��{�0�I='�18��sm�����u-����G�gU��C����0�ZK%��b��X)\ky��o��M��q՘cs���\-Z��%Γ�ש�DA9hs�l bC.��{l�c$�&2���J^�R��M^��I2Q���d�Z%'�q��������݋�W���g��6w��9�Tm05�_2"��a3��ԫ�T���^6uu �bt��j3a�_��i��A�J\@�tC��5�4���ֆ휄kC+���[���U�jd���M\)�T���\�r�e�sH��A�J[5�P7o5Ԅ�$t�����S���|j\e-���7Ѧ-�]��;#�:�M�}�N�3�S�*iu5�x�f-T��iP	�гY����)�A嫔�P�B�MY�z�a�!��*��T��4LgP��=Q@X��7_%Y��Z.x�f`���*�J��ҙ\��dEx���\�*9���^�:�l���A��u�"�,{�:c�P�[�zCX��|
�:[��_l�s�H�eȊْ�:Ӛe��A��V$�n��2m�贚�F��3�8#Sw�3���
�}�f�Z���Y��WU+�����T��lEQo���h��fg��a)gӑ-��N�������{�����ח`��!��Xy��uX��jKH(ԿZ�k*�b�����Xd��՟[�սs��#��շ�)X��Y� �+�o3
j��Zr�)�E�5N9z���!�tU?*OGJU�٫9��`���΁^諒C�C6_1��(.�U4�A&���:������X���Z?�I�q@��nx���^�,�����Y�R�(
���YS�0N�92�s��Y�Yn�q�W�;����kv�1�o��a2�s��yk�9$���挅�q{v���\¬�CO&�u�b�7��\cTl�;w����i3)���י�5���l�	�X��b�Z��D��W��t�:sq��>k5�+�Z�t}nm�7��ѺF?�י��w�x�f/[kx�Z�$�G*kM]��??ৌ?~���?���uc�y��#�	Xl�<�d�;vmG�i�?�|��0	����SѬ��F�� {��Z��x���uN��U�C7��2/\f�{t7aΞGa.��A��lBtC���ӌ��-bG-R����ag!������^��c_�8!$q>ts\ tuwβΒT��Dr�	ң���NXo؛�	ʰ�����:Y�1�#�ooa_�=l�r�~.V	VR��d�g�6�������x����C_�Ę�f���MB�D'��b_�BQ�ao.���9S�IN�߀Id�(���b��(����&E4�N=P	u����7����׉[wQ�^L�Z<$X�
�%��}u�<J}�����Ɗ��cO�h�ޚ�f֮��|<L}��.�͎1�Q{u��-P��Gn���ȣ�� `��	����5���Z�6�R�1�Jz�BCZ�ao�lM#F���c�E2צ-�yB%y�BC�O��I��e��2u̅%��Έ��d�(����Xm>�f�0�@dG���5��r��dL
�>*�x�!K�ao�\�.q��4k�Q0���:�$�Py�BC�ao��50�l,^%h�ax��Y��b�Y��N�"C��ֹ)�6gvGI�-��l�u�� �ˑ6G��:KV���� ���Z�d\S<q�����P71F��*�R�KM���H��S=>����/c�����/�����?�K���S~ʛ O���)�)�W��rG[A�Y�(��3l������w��|��EL��ӑt�T�F�����(�=�S����ObƋ0������D�Ck��Ӛc�cr������4��7������7!�G>e40���|�u�I�B�(#V��K+���v�����b��&k�?j�X�ҥ��ej�)I�+H}��;�oU�S����r�S���Ŋ��[$�-�5��w|�*���
����#Ԩ�^�hC��\$P�>�ń���V+<�W�!��6�GhF�ޫA�a�5;%L���zG��x�E����/�S�ȼ�s�L�:�d!�����~��"�X�r�~uQ��Y[����kQãՎ�6�����X�U�N�&Y�IFUdݮ��zcx�]�SC�C�>Cj�0Cz,���K�lO��$���� ����/
!��a�ʵNl��I��Ǐ��x������.E4�&�B>�f�kM�2�0}6W*1�������J�{:�R�6���=��r�'k��:�����;r��e/njq<D�nڜ<�I�MҌ���0w��PF޳2X�a�qY�"U"#e̡�U�z�lw�O{��~( ��0Ѭ�#s	2�!���2�F��������㗟�x����PPޓ*-Ҽ�f�����h��IG��@� ��C1yOY!��K'�>!���<`�N�a=��U�+I}����-�:       �      x�3�L��"�=... U�      �       x�3�����K���O�LN��4����� V�D      �      x�3�4����� ]      �   !   x�3�NM��K���K���O��4����� c��      �      x������ � �      �      x������ � �      �   �  x��U�j�0���B�J,۱�.{�:�.[I��J����dq��}��Wؓ�H�!6*�J*�����}R�~amΰ3K�fn�?P(Pa�B�'
��������Q(.ϱEE��P��o�hf=������P�J�Zj̕�#��T��X���#�=*!��T�Q<	����H-�09\��o9�6�~���\�{q�!��1���2&ޖ�dQ�œvht�d2ZO�W��֘�T�J�jY��\��pK���*\�;�o�	nH�6ц��ކI]��V�i�A��#�A�L���\W�����;'�]���Ϡ�ð�,`��qf����0��g�zMlN�w�����������ҵ�����q?6�$��ٸ�M�ɠ�tҏ��!9�Z/x�^�V�5!��ĵ�pC�~��ʯW��:H:qB��$d"F����)d=��0S�ƅ������_���6z����̂丹���/u�JY�%�@�t�l�ݩ���`�      �      x������ � �      �      x������ � �      �   $   x�3���2& �D��sa",AD� ��=       	      x������ � �      	      x������ � �      	   2   x�3�tt����2�p�t��2��u�stw�2�t�w������ �o	      �   (   x�3�4�2�4�2�4�2Ҧ@l��@lĖ@���� i;       	      x������ � �      	   �   x���M��0��3E���j�~��.e��6�P�@�e��۸�+���Mx���d�Z{k�p��`n+�ﰟ��qe����WBr/g��ڪ5��Q+���CS\
��2d�m�`��j�ޚ?���Jp���ӏi�Ɲ�ך�9p)}0�.����E�����p���o;��6.f	՛ŕ:��W�0O��8����z�sȱH������7�r���>P�q���V��      	   �  x�u�Ɏ�0�y��ٗp�Nhd�\���@����=sȌ4u�O�O��$ �_>j2^1���3(�~�8�Q2�(�c&Ù$�r�qw�p�|�yڧ(���a/�cڨh��#�x��dv51X�}=LdN��ǟ�����ߒzab��f=�jus�{�y!,�G��C�6�Mz`wr.w��@�ԿU�D��Qo^��a��>VO���YW��!���nQ~�w��W9zUZ������r6u�{��D.	�{����R�݇'�:;ޥ|�ʡ+Kg�N��&Y������r^N�t5,;A�K�k7�e��b���Bq{%��e�<sW����[&� �3�r1L=�Z?H Ud��yB-��0����(mTO���Y�g�]A�t��`p�<�	9J|�.�^C� ?���r	U�4��j��Ͼ�4�+_F��b�%�6�V=�s�[���,�S��I��R��̀fQ5q�;>ٯq��T��|M5I���R ���s����b      	      x������ � �      	      x������ � �     