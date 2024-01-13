ALTER VIEW FIDEMAX_CUSTOMER AS
SELECT A1_COD AS CODIGO,A1_LOJA AS LOJA,RTRIM(LTRIM(A1_CGC)) AS CGC,RTRIM(LTRIM(A1_NOME)) AS NOME,RTRIM(LTRIM(A1_END)) AS ENDERECO,RTRIM(LTRIM(A1_CEP)) AS CEP,
RTRIM(LTRIM(A1_BAIRRO)) AS BAIRRO,RTRIM(LTRIM(A1_COMPLEM)) AS COMPLEMENTO,
CAST(A1_DTNASC AS DATE) AS DATA_NASCIMENTO,RTRIM(LTRIM(A1_DDD)) AS DDD,RTRIM(LTRIM(A1_TEL)) AS TELEFONE,ZFB.R_E_C_N_O_ AS REC,
CAST(ZFB_DTINTE AS DATE) AS DATA_INTEGRACAO,ZFB_HINTEG AS HORA_INTEGRACAO
FROM ZFB010 (NOLOCK) ZFB INNER JOIN SA1010  (NOLOCK) A1 ON A1_COD = ZFB_CLIENT AND ZFB_LOJA = A1_LOJA
WHERE ZFB_DTINTE = '' AND ZFB.D_E_L_E_T_ = '' AND A1.D_E_L_E_T_ = ''
