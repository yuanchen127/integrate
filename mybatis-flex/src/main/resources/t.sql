insert into HRP..TBWZYPZDFZB(CSPM, CCGLBMC, CDLCGMC, CGWYPLBBM, CGWYPLBMC, CGJYPMC, CGJYPBM, BZDYP, CYBZFBL, CYBBM,
                             CYBMC, CYBJB, CLCMC, CYPCJBM, CYPCJID, CJLDW, CJLDWBM, NJBJL, BYBTSYP, CZJDW, CZJDWBM,
                             CZXBZDW, CZXBZDWBM, CYPGG, CYPGGBM, CYPGGID, CBZGG, IGZ, BGW, BGJJY, BYXJM, CLIMIT,
                             NMRYYJL, CMRSYSD, CYYTS, CMRYPYC, CMRYYTJ, IFYQZFSMZ, IFYQZFSZY, CMRSYSDDW, CYPZLFLMC,
                             CYPZLFLBM, CYPMC, CYPBM, CJXBM, CJXMC, IKSSJB, BPS, IYPZLFL, CPYM, CWJBM, CGMYWBM, CGMYWMC,
                             CDMBM, CJSBM, IYBSPBJ, CYBSPTS, IYBSPXQ, IYBSPTS, BWS, CYYTJKYS, IXZDCYYJL, IXZYYTS,
                             CSCQYMC, BFZYY, IBYLX, CBYLXMC, IYWID)
select cj.CSPM,
       cj.CCGLBMC,
       cj.CDLCGMC,
       yp.CGWYPLBBM,
       yp.CGWYPLBMC,
       cj.CGJYPMC,
       cj.CGJYPBM,
       gg.BZDYP,
       cj.CYBZFBL,
       cj.CYBBM,
       cj.CYBMC,
       cj.CYBJB,
       gg.CLCMC,
       cj.CYPCJBM,
       cj.CYPCJID,
       gg.CJLDW,
       gg.CJLDWBM,
       gg.NJBJL,
       ISNULL(gg.BYBTSYP, 0) BYBTSYP,
       gg.CZJDW,
       gg.CZJDWBM,
       gg.CZXBZDW,
       gg.CZXBZDWBM,
       gg.CYPGG,
       gg.CYPGGBM,
       gg.CYPGGID,
       gg.CBZGG,
       gg.IGZ,
       yp.BGW,
       gg.BGJJY,
       wzzdypkz.BYXJM,
       wzzdyplc.CLIMIT,
       wzzdyplc.NMRYYJL,
       wzzdyplc.CMRSYSD,
       wzzdyplc.CYYTS,
       wzzdyplc.CMRYPYC,
       wzzdyplc.CMRYYTJ,
       wzzdyplc.IFYQZFSMZ,
       wzzdyplc.IFYQZFSZY,
       wzzdyplc.CMRSYSDDW,
       yp.CYPZLFLMC,
       yp.CYPZLFLBM,
       yp.CYPMC,
       yp.CYPBM,
       yp.CJXBM,
       yp.CJXMC,
       yp.IKSSJB,
       yp.BPS,
       yp.IYPZLFL,
       yp.CPYM,
       yp.CWJBM,
       yp.CGMYWBM,
       yp.CGMYWMC,
       yp.CDMBM,
       yp.CJSBM,
       ISNULL(yp.IYBSPBJ, 1) IYBSPBJ,
       yp.CYBSPTS,
       yp.IYBSPXQ,
       yp.IYBSPTS,
       yp.BWS,
       wzzdypkz.CYYTJKYS,
       wzzdypkz.IXZDCYYJL,
       wzzdypkz.IXZYYTS,
       cj.CSCQYMC,
       gg.BFZYY,
       gg.IBYLX,
       gg.CBYLXMC,
       '1866737672295362562'
FROM HRP..TBWZZDYPCJ cj WITH ( NOLOCK )   LEFT JOIN HRP..TBWZZDYPGG gg
WITH ( NOLOCK )
ON gg.CYPGGID = cj.CYPGGID LEFT JOIN HRP..TBWZZDYPZYBL wzzdypzybl
WITH (NOLOCK)
ON wzzdypzybl.CYPGGID = gg.CYPGGID AND wzzdypzybl.CRYLBBM = '' LEFT JOIN HRP..TBWZZDYPMZBL wzzdypmzbl
WITH (NOLOCK)
ON wzzdypmzbl.CYPGGID = gg.CYPGGID AND wzzdypmzbl.CRYLBBM = '' LEFT JOIN HRP..TBWZZDYP yp
WITH ( NOLOCK )
ON yp.CYPID = gg.CYPID LEFT JOIN HRP..TBWZZDYPKZ wzzdypkz
WITH ( NOLOCK )
ON wzzdypkz.CYPGGID = gg.CYPGGID LEFT JOIN HRP..TBWZZDYPLC wzzdyplc
WITH ( NOLOCK )
ON wzzdyplc.CYPGGID = gg.CYPGGID
WHERE cj.BENABLE= 1
  AND gg.BENABLE= 1
  AND yp.BENABLE= 1
  AND yp.CYPZLFLBM IN ('1'
    , '2'
    , '3')
  AND (yp.CYPBM LIKE CONCAT ('%'
    , 'ww'
    , '%')
   OR yp.CPYM LIKE CONCAT ('%'
    , 'ww'
    , '%')
   OR yp.CYPMC LIKE CONCAT ('%'
    , 'ww'
    , '%')
   OR yp.CYPMCMW LIKE CONCAT ('%'
    , 'ww'
    , '%')
   OR cj.CYPCJBM LIKE CONCAT('%'
    , 'ww'
    , '%')
   OR gg.CLCMC LIKE CONCAT('%'
    , 'ww'
    , '%')
   OR gg.CLCPYM LIKE CONCAT('%'
    , 'ww'
    , '%')
   OR cj.CSPM LIKE CONCAT('%'
    , 'ww'
    , '%')
   OR cj.CSPMPYM LIKE CONCAT('%'
    , 'ww'
    , '%')
   or gg.CBM1 like concat('%'
    , 'ww'
    , '%')
   or gg.CBM2 like concat('%'
    , 'ww'
    , '%')
   or gg.CBM3 like concat('%'
    , 'ww'
    , '%')
   or gg.CBM4 like concat('%'
    , 'ww'
    , '%') )
  AND ( EXISTS (SELECT 1 FROM HRP..TBWZZDYPKSYYXZ yyxz WITH (NOLOCK) WHERE yyxz.ILX != 2
  and yyxz.CYPGGID = gg.CYPGGID
  AND yyxz.CXZMZKSBM = 'ww' )
   OR wzzdypkz.CXZKSMZS= '' )
  AND wzzdypkz.BENABLEMZ = 1
  AND ( EXISTS (SELECT 1 FROM HRP..TBWZZDYPYSYYXZ kyys WITH (NOLOCK) WHERE kyys.CYPGGID = gg.CYPGGID
  AND kyys.CYSID = 'ww' )
   or not EXISTS ( SELECT * FROM HRP..TBWZZDYPYSYYXZ kyys WITH ( NOLOCK ) WHERE kyys.CYPGGID = gg.CYPGGID ))
  AND ( not EXISTS (SELECT 1 FROM HRP..TBWZZDYPKSYYXZ ypxz WITH (NOLOCK) WHERE ypxz.ILX = 2
  and ypxz.CYPGGID = gg.CYPGGID
  and ypxz.CXZMZKSBM = 'ww' ) )
  AND cj.CJGID = '1593138685300248579';
insert into HRP..TBWZYPYJFZB (IYWID, CPCID, ICKSL)
SELECT '1866737672295362563_' + yj.CKWBM IYWID, CPCID, SUM(ISNULL(ICKSL, 0)) ICKSL
FROM HRP..TBWZYPZDFZB zd
         LEFT JOIN HRP..TBWZYPKCYJ_400 yj WITH ( NOLOCK )
on zd.CYPCJID = yj.CYPCJID
WHERE yj.CYPCJID is not NULL and zd.IYWID = '1866737672295362562'
GROUP BY CPCID, '1866737672295362563_'+yj.CKWBM;
insert into HRP..TBWZYPKCFZB (IYWID, CYPCJID, CCKDW, CCKDWBM, IZXHSXS, BCL, CJGID, CKWBM, CPCID, BMR, CPCBM, IKCKSL,
                              IKYSL, IKCSL, IKCCLSL)
SELECT '1866737672295362564_' + ypkc.CKWBM                                                          IYWID,
       ypkc.CYPCJID,
       ypkc.CCKDW,
       ypkc.CCKDWBM,
       ypkc.IZXHSXS,
       ypkc.BCL,
       ypkc.CJGID,
       ypkc.CKWBM,
       ypkc.CPCID,
       ypkc.BMR,
       ypkc.CPCBM,
       ISNULL(ypkc.IKCKSL, 0)                                                                       IKCKSL,
       ISNULL(CASE
                  WHEN ypkc.BCL = 1 THEN (ypkc.IKCKSL - ISNULL(yj.ICKSL, 0)) - (ISNULL(ypkc.ITYSL, 0) * ypkc.IZXHSXS)
                  ELSE ISNULL(ypkc.IKCKSL, 0) - ISNULL(yj.ICKSL, 0) - ISNULL(ypkc.ITYSL, 0) END, 0) IKYSL,
       CASE
           WHEN ypkc.BCL = 1 THEN convert(int, ISNULL(ypkc.IKCKSL, 0) - ISNULL(yj.ICKSL, 0)) / ISNULL(ypkc.IZXHSXS, 1) -
                                  ISNULL(ypkc.ITYSL, 0)
           ELSE ISNULL(ypkc.IKCKSL, 0) - ISNULL(yj.ICKSL, 0) - ISNULL(ypkc.ITYSL, 0) END            IKCSL,
       CASE
           WHEN ypkc.BCL = 1
               THEN convert(int, (ISNULL(ypkc.IKCKSL, 0) - ISNULL(yj.ICKSL, 0))) % ISNULL( ypkc.IZXHSXS, 1 )
           ELSE 0 END                                                                               IKCCLSL
FROM HRP..TBWZYPZDFZB zd
         LEFT JOIN HRP..TBWZYPKC_400 ypkc WITH ( NOLOCK )
ON zd.CYPCJID= ypkc.CYPCJID LEFT JOIN HRP..TBWZYPYJFZB yj ON ypkc.CPCID= yj.CPCID and yj.IYWID ='1866737672295362563_'+ypkc.CKWBM
WHERE ypkc.BENABLE = 1
  AND ypkc.CJGID = '1593138685300248579'
  AND ypkc.CYPCJID is not null
  and zd.IYWID = '1866737672295362562';
insert into HRP..TBWZYPTEMPFZB (IYWID, CYPCJID, CKWBM, IKYSL, IKCKSL, IKCSL, IKCCLSL)
select 1866737672295362565,
       CYPCJID,
       ckwbm,
       sum(IKYSL)   IKYSL,
       sum(IKCKSL)  IKCKSL,
       sum(IKCSL)   IKCSL,
       sum(IKCCLSL) IKCCLSL
from (select CYPCJID, ckwbm, IKYSL, IKCKSL, IKCSL, IKCCLSL
      from HRP..TBWZYPKCFZB
      where ikysl > 0
        and IYWID = '1866737672295362564_400') t
group by CYPCJID, ckwbm;
insert into HRP..TBWZYPCJFZB (IYWID, SORT, CKWMC, CCKDW, CCKDWBM, CKWBM, BCL, IZXHSXS, MLSJ, MCLLSJ, MJHJ, MCLJHJ, BDL,
                              BGJTPYW, CYPCJID, BMR, DYXQ, DRKSJ, IKCKSL)
select 1866737672295362566, t.*
from (SELECT ROW_NUMBER() OVER ( partition BY wzypkc.CYPCJID ORDER BY BMR DESC, DYXQ ASC, DRKSJ ASC, IKCKSL DESC ) sort, kszd.CKSMC CKWMC,
             wzypkc.CCKDW,
             wzypkc.CCKDWBM,
             wzypkc.CKWBM,
             wzypkc.BCL,
             wzypkc.IZXHSXS,
             kcpc.MLSJ,
             kcpc.MCLLSJ,
             kcpc.MJHJ,
             kcpc.MCLJHJ,
             kcpc.BDL,
             kcpc.BGJTPYW,
             wzypkc.CYPCJID,
             wzypkc.BMR,
             kcpc.DYXQ,
             kcpc.DRKSJ,
             wzypkc.IKCKSL
      from HRP..TBWZYPKCFZB wzypkc
               LEFT JOIN HRP..TBWZYPKCPC kcpc WITH(NOLOCK)
      on wzypkc.CPCID = kcpc.CPCID LEFT JOIN HISBM..TBKSZD kszd
      WITH ( NOLOCK )
      ON kszd.CKSBM = wzypkc.CKWBM AND kszd.CJGID = wzypkc.CJGID
      WHERE wzypkc.ikysl>0 and wzypkc.IYWID = '1866737672295362564_400') t;
select top 20 temp.IKYSL,temp.IKCKSL,
       temp.IKCSL,
       temp.IKCCLSL,
       zd.CSPM,
       zd.CCGLBMC,
       zd.CDLCGMC,
       zd.CGWYPLBBM,
       zd.CGWYPLBMC,
       zd.CGJYPMC,
       zd.CGJYPBM,
       zd.BZDYP,
       zd.CYBZFBL,
       zd.CYBBM,
       zd.CYBMC,
       zd.CYBJB,
       zd.CLCMC,
       zd.CYPCJBM,
       zd.CJLDW,
       zd.CJLDWBM,
       zd.NJBJL,
       ISNULL(zd.BYBTSYP, 0) BYBTSYP,
       zd.CZJDW,
       zd.CZJDWBM,
       zd.CZXBZDW,
       zd.CZXBZDWBM,
       zd.CYPGG,
       zd.CYPGGBM,
       zd.CYPGGID,
       zd.CBZGG,
       zd.IGZ,
       zd.BGW,
       zd.BGJJY,
       zd.BYXJM,
       zd.CLIMIT,
       zd.NMRYYJL,
       zd.CMRSYSD,
       zd.CYYTS,
       zd.CMRYPYC,
       zd.CMRYYTJ,
       zd.IFYQZFSMZ,
       zd.IFYQZFSZY,
       zd.CMRSYSDDW,
       zd.CYPZLFLMC,
       zd.CYPZLFLBM,
       zd.CYPMC,
       zd.CYPBM,
       zd.CJXBM,
       zd.CJXMC,
       zd.IKSSJB,
       zd.BPS,
       zd.IYPZLFL,
       zd.CPYM,
       zd.CWJBM,
       zd.CGMYWBM,
       zd.CGMYWMC,
       zd.CDMBM,
       zd.CJSBM,
       ISNULL(zd.IYBSPBJ, 1) IYBSPBJ,
       zd.CYBSPTS,
       zd.IYBSPXQ,
       zd.IYBSPTS,
       zd.BWS,
       zd.CYYTJKYS,
       zd.IXZDCYYJL,
       zd.IXZYYTS,
       zd.CSCQYMC,
       zd.BFZYY,
       zd.IBYLX,
       zd.CBYLXMC,
       cj.*
from HRP..TBWZYPTEMPFZB temp
         LEFT JOIN HRP..TBWZYPZDFZB zd ON temp.CYPCJID = zd.CYPCJID and zd.IYWID = '1866737672295362562'
         LEFT JOIN HRP..TBWZYPCJFZB cj
                   ON temp.CYPCJID = cj.CYPCJID AND temp.CKWBM = cj.CKWBM and cj.IYWID = '1866737672295362566'
         LEFT JOIN HISBM..TBKSSX sx WITH ( NOLOCK )
ON CJ.CKWBM = sx.CKSBM AND sx.CJGID ='1593138685300248579' AND sx.CKSSXBM = 'YPKW_KWZL' LEFT JOIN HRP..TBWZZDYPKWZL kwzl
WITH ( NOLOCK )
ON sx.CKSSXZ = kwzl.CBM
where cj.sort = 1 and temp.IYWID = '1866737672295362565'
order by len(zd.CYPMC), kwzl.IKWJB, zd.cypggbm, zd.cypcjbm