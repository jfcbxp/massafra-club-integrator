CREATE VIEW FIDEMAX_ORDER_REFUND_PROFESSIONAL AS
SELECT DISTINCT LTRIM(RTRIM(F2_FILIAL)) AS EMPRESA,LTRIM(RTRIM(F2_DOC)) AS DOCUMENTO,LTRIM(RTRIM(F2_SERIE)) AS SERIE,CAST(F2_EMISSAO AS DATE) AS EMISSAO,LTRIM(RTRIM(A1_CGC)) AS CGC,LTRIM(RTRIM(A1_DDD)) AS DDD,LTRIM(RTRIM(A1_TEL)) AS TELEFONE,
ZFB_CLIENT AS CODIGO_PROFISSIONAL,LTRIM(RTRIM(A1_NOME)) AS NOME_PROFISSIONAL ,
F2_CLIENTE AS CODIGO_CLIENTE,F2_LOJA AS LOJA_CLIENTE,(SELECT LTRIM(RTRIM(A11.A1_NOME)) FROM SA1010 (NOLOCK) A11 WHERE F2_CLIENTE = A11.A1_COD AND F2_LOJA = A11.A1_LOJA AND A11.D_E_L_E_T_ = '') AS NOME_CLIENTE,
ZFE_COD AS CODIGO_TIPO,LTRIM(RTRIM(ZFE_DESCRI)) AS DESCRICAO_TIPO,ZFE_FIDPER AS PERCENTUAL_TIPO,F1.R_E_C_N_O_ AS REC
FROM SF1010 (NOLOCK) F1
INNER JOIN SD1010 (NOLOCK) D1 ON D1_FILIAL = F1_FILIAL AND D1_DOC = F1_DOC AND D1_SERIE = F1_SERIE
INNER JOIN SD2010 (NOLOCK) D2 ON D1_FILORI = D2_FILIAL AND D1_NFORI = D2_DOC AND D1_SERIORI = D2_SERIE
INNER JOIN SF2010 (NOLOCK) F2 ON F2_FILIAL = D2_FILIAL AND F2_DOC = D2_DOC AND F2_SERIE = D2_SERIE
INNER JOIN ZFB010 (NOLOCK) ZFB ON ZFB_CLIENT = F2_VEND2
INNER JOIN ZFE010 (NOLOCK) ZFE ON ZFB_TIPOCL = ZFE_COD
INNER JOIN SA1010 (NOLOCK) A1 ON A1_COD = ZFB_CLIENT
AND F2_EMISSAO >= ZFB_DATA
WHERE F1_TIPO  = 'D' AND F2_ZDTFIDP <> '' AND F1_ZDTFIDP = '' AND ZFB_DTINTE <> '' AND
F1.D_E_L_E_T_ = '' AND D1.D_E_L_E_T_ = '' AND D2.D_E_L_E_T_ = '' AND
F2.D_E_L_E_T_ = '' AND ZFB.D_E_L_E_T_ = '' AND ZFE.D_E_L_E_T_ = '' AND A1.D_E_L_E_T_ = ''