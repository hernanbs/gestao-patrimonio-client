--
-- PostgreSQL database dump
--

-- Dumped from database version 11.8
-- Dumped by pg_dump version 11.8

-- Started on 2020-08-16 21:26:23

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 197 (class 1259 OID 16396)
-- Name: tb_marca; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_marca (
    marca_id integer NOT NULL,
    nome character varying(100) NOT NULL
);


ALTER TABLE public.tb_marca OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 16394)
-- Name: tb_marca_marca_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tb_marca_marca_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tb_marca_marca_id_seq OWNER TO postgres;

--
-- TOC entry 2828 (class 0 OID 0)
-- Dependencies: 196
-- Name: tb_marca_marca_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tb_marca_marca_id_seq OWNED BY public.tb_marca.marca_id;


--
-- TOC entry 199 (class 1259 OID 16404)
-- Name: tb_patrimonio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_patrimonio (
    patrimonio_id integer NOT NULL,
    patrimonio_marca_id integer NOT NULL,
    patrimonio_nome character varying(100) NOT NULL,
    patrimonio_descricao character varying(255),
    patrimonio_numero_tombo integer
);


ALTER TABLE public.tb_patrimonio OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 16402)
-- Name: tb_patrimonio_patrimonio_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tb_patrimonio_patrimonio_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tb_patrimonio_patrimonio_id_seq OWNER TO postgres;

--
-- TOC entry 2829 (class 0 OID 0)
-- Dependencies: 198
-- Name: tb_patrimonio_patrimonio_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tb_patrimonio_patrimonio_id_seq OWNED BY public.tb_patrimonio.patrimonio_id;


--
-- TOC entry 2691 (class 2604 OID 16399)
-- Name: tb_marca marca_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_marca ALTER COLUMN marca_id SET DEFAULT nextval('public.tb_marca_marca_id_seq'::regclass);


--
-- TOC entry 2692 (class 2604 OID 16407)
-- Name: tb_patrimonio patrimonio_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_patrimonio ALTER COLUMN patrimonio_id SET DEFAULT nextval('public.tb_patrimonio_patrimonio_id_seq'::regclass);


--
-- TOC entry 2820 (class 0 OID 16396)
-- Dependencies: 197
-- Data for Name: tb_marca; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_marca (marca_id, nome) FROM stdin;
2	Flexform Lite
3	Aridi
28	Hargrivis
38	Flexform Lite 11
39	Hernan
41	2
\.


--
-- TOC entry 2822 (class 0 OID 16404)
-- Dependencies: 199
-- Data for Name: tb_patrimonio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_patrimonio (patrimonio_id, patrimonio_marca_id, patrimonio_nome, patrimonio_descricao, patrimonio_numero_tombo) FROM stdin;
4	2	outro teste	outro teste	111111
10	3	teste 9	teste 9	621805
11	3	teste 9	teste 9	905760
12	3	MESANINO	MESANINO	588219
\.


--
-- TOC entry 2830 (class 0 OID 0)
-- Dependencies: 196
-- Name: tb_marca_marca_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_marca_marca_id_seq', 41, true);


--
-- TOC entry 2831 (class 0 OID 0)
-- Dependencies: 198
-- Name: tb_patrimonio_patrimonio_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_patrimonio_patrimonio_id_seq', 13, true);


--
-- TOC entry 2694 (class 2606 OID 16401)
-- Name: tb_marca tb_marca_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_marca
    ADD CONSTRAINT tb_marca_pkey PRIMARY KEY (marca_id);


--
-- TOC entry 2696 (class 2606 OID 16409)
-- Name: tb_patrimonio tb_patrimonio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_patrimonio
    ADD CONSTRAINT tb_patrimonio_pkey PRIMARY KEY (patrimonio_id);


--
-- TOC entry 2697 (class 2606 OID 16415)
-- Name: tb_patrimonio fk_patrimonio_marca_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_patrimonio
    ADD CONSTRAINT fk_patrimonio_marca_id FOREIGN KEY (patrimonio_marca_id) REFERENCES public.tb_marca(marca_id) ON DELETE CASCADE;


-- Completed on 2020-08-16 21:26:23

--
-- PostgreSQL database dump complete
--

