--
-- PostgreSQL database dump
--

-- Dumped from database version 17.0
-- Dumped by pg_dump version 17.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: igraci; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.igraci (
    id integer NOT NULL,
    ime character varying(50) NOT NULL,
    prezime character varying(50) NOT NULL,
    pozicija character varying(30),
    broj_dresa integer,
    godine integer,
    visina integer,
    tezina integer,
    drzava character varying(50),
    godina_pridruzivanja integer
);


ALTER TABLE public.igraci OWNER TO postgres;

--
-- Name: igraci_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.igraci_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.igraci_id_seq OWNER TO postgres;

--
-- Name: igraci_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.igraci_id_seq OWNED BY public.igraci.id;


--
-- Name: prosli_klubovi; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.prosli_klubovi (
    id integer NOT NULL,
    igrac_id integer,
    klub character varying(50) NOT NULL,
    godina_pocetka integer,
    godina_zavrsetka integer
);


ALTER TABLE public.prosli_klubovi OWNER TO postgres;

--
-- Name: prosli_klubovi_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.prosli_klubovi_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.prosli_klubovi_id_seq OWNER TO postgres;

--
-- Name: prosli_klubovi_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.prosli_klubovi_id_seq OWNED BY public.prosli_klubovi.id;


--
-- Name: igraci id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.igraci ALTER COLUMN id SET DEFAULT nextval('public.igraci_id_seq'::regclass);


--
-- Name: prosli_klubovi id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prosli_klubovi ALTER COLUMN id SET DEFAULT nextval('public.prosli_klubovi_id_seq'::regclass);


--
-- Data for Name: igraci; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.igraci (id, ime, prezime, pozicija, broj_dresa, godine, visina, tezina, drzava, godina_pridruzivanja) FROM stdin;
1	Mohamed	Salah	Napada─ì	11	32	175	71	Egipat	2017
2	Virgil	van Dijk	Brani─ì	4	33	193	92	Nizozemska	2018
3	Luis	D├¡az	Napada─ì	7	27	175	70	Kolumbija	2022
4	Diogo	Jota	Napada─ì	20	27	178	76	Portugal	2020
5	Cody	Gakpo	Napada─ì	18	25	186	80	Nizozemska	2023
6	Alisson	Becker	Golman	1	32	193	92	Brazil	2018
7	Trent	Alexander-Arnold	Brani─ì	66	26	175	64	Engleska	2016
8	Andrew	Robertson	Brani─ì	26	30	178	72	┼ákotska	2017
9	Darwin	N├║├▒ez	Napada─ì	9	25	187	81	Urugvaj	2022
10	Wataru	Endo	Sredi┼ínji vezni	3	31	178	77	Japan	2023
\.


--
-- Data for Name: prosli_klubovi; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.prosli_klubovi (id, igrac_id, klub, godina_pocetka, godina_zavrsetka) FROM stdin;
1	1	Chelsea	2014	2016
2	1	AS Roma	2016	2017
3	2	Groningen	2011	2013
4	2	Celtic	2013	2015
5	2	Southampton	2015	2018
6	3	FC Porto	2020	2022
7	4	Wolverhampton	2018	2020
8	5	PSV Eindhoven	2016	2023
9	6	AS Roma	2016	2018
10	8	Hull City	2014	2017
11	9	Benfica	2020	2022
12	10	VfB Stuttgart	2020	2023
\.


--
-- Name: igraci_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.igraci_id_seq', 10, true);


--
-- Name: prosli_klubovi_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.prosli_klubovi_id_seq', 12, true);


--
-- Name: igraci igraci_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.igraci
    ADD CONSTRAINT igraci_pkey PRIMARY KEY (id);


--
-- Name: prosli_klubovi prosli_klubovi_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prosli_klubovi
    ADD CONSTRAINT prosli_klubovi_pkey PRIMARY KEY (id);


--
-- Name: prosli_klubovi prosli_klubovi_igrac_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prosli_klubovi
    ADD CONSTRAINT prosli_klubovi_igrac_id_fkey FOREIGN KEY (igrac_id) REFERENCES public.igraci(id) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

