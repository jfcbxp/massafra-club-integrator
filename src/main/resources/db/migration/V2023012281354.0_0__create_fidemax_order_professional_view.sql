CREATE VIEW FIDEMAX_ORDER_PROFESSIONAL AS
SELECT F2_FILIAL AS EMPRESA,F2_DOC AS DOCUMENTO,F2_SERIE AS SERIE,CAST(F2_EMISSAO AS DATE) AS EMISSAO,A1_CGC AS CGC,A1_DDD AS DDD,A1_TEL AS TELEFONE,ZFB_CLIENT AS CODIGO_PROFISSIONAL,A1_NOME AS NOME_PROFISSIONAL ,
F2_CLIENTE AS CODIGO_CLIENTE,F2_LOJA AS LOJA_CLIENTE,(SELECT A11.A1_NOME FROM SA1010 (NOLOCK) A11 WHERE F2_CLIENTE = A11.A1_COD AND F2_LOJA = A11.A1_LOJA AND A11.D_E_L_E_T_ = '') AS NOME_CLIENTE,
ZFE_COD AS CODIGO_TIPO,ZFE_DESCRI AS DESCRICAO_TIPO,ZFE_FIDPER AS PERCENTUAL_TIPO,F2.R_E_C_N_O_ AS REC
FROM SF2010 (NOLOCK) F2 
INNER JOIN ZFB010 (NOLOCK) ZFB ON ZFB_CLIENT = F2_VEND2
INNER JOIN ZFE010 (NOLOCK) ZFE ON ZFB_TIPOCL = ZFE_COD
INNER JOIN SA1010 (NOLOCK) A1 ON A1_COD = ZFB_CLIENT
AND F2_EMISSAO >= ZFB_DATA 
WHERE F2_ZDTFIDP = '' AND ZFB_DTINTE <> '' AND F2.D_E_L_E_T_ = '' AND F2.D_E_L_E_T_ = '' AND A1.D_E_L_E_T_ = '' AND ZFE.D_E_L_E_T_ = ''