package com.leadmap.environmentalrotection.common.util;

import com.leadmap.environmentalrotection.dao.AirQualityInfoDao;
import com.leadmap.environmentalrotection.entity.weather.AirQualityInfo;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:从数据库中将数据导入exccel
 *
 * @author: yxm
 * @Date: 2018/9/14 15:22
 */
@Component
public class ExcelUtil {

    @Autowired
    private AirQualityInfoDao educationAppInfoDao;

    public void dataToExcel() throws IOException {

        WritableWorkbook writableWorkbook;

        String fileName = "E://environment.xls";
        File file = new File(fileName);
        if(!file.exists()){
            file.createNewFile();
        }
        writableWorkbook = Workbook.createWorkbook(file);
        WritableSheet writableSheet1 = writableWorkbook.createSheet("AirIndicatorInfo",0);
        WritableSheet writableSheet = writableWorkbook.createSheet("AirRankingInfo",1);

        List<AirQualityInfo> educationAppInfoList =  educationAppInfoDao.getEducationAppInfoList();

        Label b0= new Label(0, 0, "keyId");
        Label b1= new Label(1, 0, "aqi");
        Label b2= new Label(2, 0, "aqi24h");
        Label b3= new Label(3, 0, "co");
        Label b4= new Label(4, 0, "co24h");
        Label b5= new Label(5, 0, "co_01");
        Label b6= new Label(6,0, "co_02");
        Label b7= new Label(7, 0, "coavg");
        Label b8= new Label(8, 0, "cofacesign");
        Label b9= new Label(9, 0, "colevel");
        Label b10= new Label(10, 0, "coquality");
        Label b11= new Label(11, 0, "createTime");
        Label b12= new Label(12, 0, "date");
        Label b13= new Label(13, 0, "date_f");
        Label b14= new Label(14, 0, "first");
        Label b15= new Label(15, 0, "grade");
        Label b16= new Label(16, 0, "id");
        Label b17= new Label(17, 0, "isCountryStation");
        Label b18= new Label(18, 0, "no2");
        Label b19= new Label(19, 0, "no224h");
        Label b20= new Label(20, 0, "no2_01");
        Label b21= new Label(21, 0, "no2_02");
        Label b22= new Label(22, 0, "no2avg");
        Label b23= new Label(23, 0, "no2facesign");
        Label b24= new Label(24, 0, "no2level");
        Label b25= new Label(25, 0, "no2quality");
        Label b26= new Label(26, 0, "O3");
        Label b27= new Label(27, 0, "O324h");
        Label b28= new Label(28, 0, "O3_01");
        Label b29= new Label(29, 0, "O3_02");
        Label b30= new Label(30, 0, "O3avg");
        Label b31= new Label(31, 0, "O3facesign");
        Label b32= new Label(32, 0, "O3level");
        Label b33= new Label(33, 0, "O3quality");
        Label b34= new Label(34, 0, "pm10");
        Label b35= new Label(35, 0, "pm1024h");
        Label b36= new Label(36, 0, "pm10_01");
        Label b37= new Label(37, 0, "pm10_02");
        Label b38= new Label(38, 0, "pm10avg");
        Label b39= new Label(39, 0, "pm10facesign");
        Label b40= new Label(40, 0, "pmlevel");
        Label b41= new Label(41, 0, "pm10quality");
        Label b42= new Label(42, 0, "pm2");
        Label b43= new Label(43, 0, "pm2524h");
        Label b44= new Label(44, 0, "pm25avg");
        Label b45= new Label(45, 0, "pm2_01");
        Label b46= new Label(46, 0, "pm2_02");
        Label b47= new Label(47, 0, "so2");
        Label b48= new Label(48, 0, "so224h");
        Label b49= new Label(49, 0, "so2_01");
        Label b50= new Label(50, 0, "so2_02");
        Label b51= new Label(51, 0, "so2avg");
        Label b52= new Label(52, 0, "so2facesign");
        Label b53= new Label(53, 0, "so2level");
        Label b54= new Label(54, 0, "so2quality");
        Label b55= new Label(55, 0, "stationName");
        Label b56= new Label(56, 0, "x");
        Label b57= new Label(57, 0, "y");

        try {
            writableSheet.addCell(b0);
            writableSheet.addCell(b1);
            writableSheet.addCell(b2);
            writableSheet.addCell(b3);
            writableSheet.addCell(b4);
            writableSheet.addCell(b5);
            writableSheet.addCell(b6);
            writableSheet.addCell(b7);
            writableSheet.addCell(b8);
            writableSheet.addCell(b9);
            writableSheet.addCell(b10);
            writableSheet.addCell(b11);
            writableSheet.addCell(b12);
            writableSheet.addCell(b13);
            writableSheet.addCell(b14);
            writableSheet.addCell(b15);
            writableSheet.addCell(b16);
            writableSheet.addCell(b17);
            writableSheet.addCell(b18);
            writableSheet.addCell(b19);
            writableSheet.addCell(b20);
            writableSheet.addCell(b21);
            writableSheet.addCell(b22);
            writableSheet.addCell(b23);
            writableSheet.addCell(b24);
            writableSheet.addCell(b25);
            writableSheet.addCell(b26);
            writableSheet.addCell(b27);
            writableSheet.addCell(b28);
            writableSheet.addCell(b29);
            writableSheet.addCell(b30);
            writableSheet.addCell(b31);
            writableSheet.addCell(b32);
            writableSheet.addCell(b33);
            writableSheet.addCell(b34);
            writableSheet.addCell(b35);
            writableSheet.addCell(b36);
            writableSheet.addCell(b37);
            writableSheet.addCell(b38);
            writableSheet.addCell(b39);
            writableSheet.addCell(b40);
            writableSheet.addCell(b41);
            writableSheet.addCell(b42);
            writableSheet.addCell(b43);
            writableSheet.addCell(b44);
            writableSheet.addCell(b45);
            writableSheet.addCell(b46);
            writableSheet.addCell(b47);
            writableSheet.addCell(b48);
            writableSheet.addCell(b49);
            writableSheet.addCell(b50);
            writableSheet.addCell(b51);
            writableSheet.addCell(b52);
            writableSheet.addCell(b53);
            writableSheet.addCell(b54);
            writableSheet.addCell(b55);
            writableSheet.addCell(b56);
            writableSheet.addCell(b57);
            for (int i = 0; i < educationAppInfoList.size(); i++){
                Label a0= new Label(0, i+1, educationAppInfoList.get(i).getKeyId()+"");
                Label a1= new Label(1, i+1, educationAppInfoList.get(i).getAqi());
                Label a2= new Label(2, i+1, educationAppInfoList.get(i).getAqi24h());
                Label a3 = new Label(3, i+1, educationAppInfoList.get(i).getCo());
                Label a4= new Label(4, i+1, educationAppInfoList.get(i).getCo24h());
                Label a5= new Label(5, i+1, educationAppInfoList.get(i).getCo_01());
                Label a6= new Label(6, i+1, educationAppInfoList.get(i).getCo_02());
                Label a7= new Label(7, i+1, educationAppInfoList.get(i).getCoavg());
                Label a8= new Label(8, i+1, educationAppInfoList.get(i).getCofacesign());
                Label a9= new Label(9, i+1, educationAppInfoList.get(i).getColevel());
                Label a10= new Label(10, i+1, educationAppInfoList.get(i).getCoquality());
                Label a11= new Label(11, i+1, educationAppInfoList.get(i).getCreateTime()+"");
                Label a12= new Label(12, i+1, educationAppInfoList.get(i).getDate()+"");
                Label a13= new Label(13, i+1, educationAppInfoList.get(i).getDate_f());
                Label a14= new Label(14, i+1, educationAppInfoList.get(i).getFirst());
                Label a15= new Label(15, i+1, educationAppInfoList.get(i).getGrade());
                Label a16= new Label(16, i+1, educationAppInfoList.get(i).getId());
                Label a17= new Label(17, i+1, educationAppInfoList.get(i).isCountryStation()+"");
                Label a18= new Label(18, i+1, educationAppInfoList.get(i).getNo2());
                Label a19= new Label(19, i+1, educationAppInfoList.get(i).getNo224h());
                Label a20= new Label(20, i+1, educationAppInfoList.get(i).getNo2_01());
                Label a21= new Label(21, i+1, educationAppInfoList.get(i).getNo2_02());
                Label a22= new Label(22, i+1, educationAppInfoList.get(i).getNo2avg());
                Label a23= new Label(23, i+1, educationAppInfoList.get(i).getNo2facesign());
                Label a24= new Label(24, i+1, educationAppInfoList.get(i).getNo2level());
                Label a25= new Label(25, i+1, educationAppInfoList.get(i).getNo2quality());
                Label a26= new Label(26, i+1, educationAppInfoList.get(i).getO3());
                Label a27= new Label(27, i+1, educationAppInfoList.get(i).getO324h());
                Label a28= new Label(28, i+1, educationAppInfoList.get(i).getO3_01());
                Label a29= new Label(29, i+1, educationAppInfoList.get(i).getO3_02());
                Label a30= new Label(30, i+1, educationAppInfoList.get(i).getO3avg());
                Label a31= new Label(31, i+1, educationAppInfoList.get(i).getO3facesign());
                Label a32= new Label(32, i+1, educationAppInfoList.get(i).getO3level());
                Label a33= new Label(33, i+1, educationAppInfoList.get(i).getO3quality());
                Label a34= new Label(34, i+1, educationAppInfoList.get(i).getPm10());
                Label a35= new Label(35, i+1, educationAppInfoList.get(i).getPm1024h());
                Label a36= new Label(36, i+1, educationAppInfoList.get(i).getPm10_01());
                Label a37= new Label(37, i+1, educationAppInfoList.get(i).getPm10_02());
                Label a38= new Label(38, i+1, educationAppInfoList.get(i).getPm10avg());
                Label a39= new Label(39, i+1, educationAppInfoList.get(i).getPm10facesign());
                Label a40= new Label(40, i+1, educationAppInfoList.get(i).getPm10level());
                Label a41= new Label(41, i+1, educationAppInfoList.get(i).getPm10quality());
                Label a42= new Label(42, i+1, educationAppInfoList.get(i).getPm2());
                Label a43= new Label(43, i+1, educationAppInfoList.get(i).getPm2524h());
                Label a44= new Label(44, i+1, educationAppInfoList.get(i).getPm25avg());
                Label a45= new Label(45, i+1, educationAppInfoList.get(i).getPm2_01());
                Label a46= new Label(46, i+1, educationAppInfoList.get(i).getPm2_02());
                Label a47= new Label(47, i+1, educationAppInfoList.get(i).getSo2());
                Label a48= new Label(48, i+1, educationAppInfoList.get(i).getSo224h());
                Label a49= new Label(49, i+1, educationAppInfoList.get(i).getSo2_01());
                Label a50= new Label(50, i+1, educationAppInfoList.get(i).getSo2_02());
                Label a51= new Label(51, i+1, educationAppInfoList.get(i).getSo2avg());
                Label a52= new Label(52, i+1, educationAppInfoList.get(i).getSo2facesign());
                Label a53= new Label(53, i+1, educationAppInfoList.get(i).getSo2level());
                Label a54= new Label(54, i+1, educationAppInfoList.get(i).getSo2quality());
                Label a55= new Label(55, i+1, educationAppInfoList.get(i).getStationName());
                Label a56= new Label(56, i+1, educationAppInfoList.get(i).getX());
                Label a57= new Label(57, i+1, educationAppInfoList.get(i).getY());
                writableSheet.addCell(a0);
                writableSheet.addCell(a1);
                writableSheet.addCell(a2);
                writableSheet.addCell(a3);
                writableSheet.addCell(a4);
                writableSheet.addCell(a5);
                writableSheet.addCell(a6);
                writableSheet.addCell(a7);
                writableSheet.addCell(a8);
                writableSheet.addCell(a9);
                writableSheet.addCell(a10);
                writableSheet.addCell(a11);
                writableSheet.addCell(a12);
                writableSheet.addCell(a13);
                writableSheet.addCell(a14);
                writableSheet.addCell(a15);
                writableSheet.addCell(a16);
                writableSheet.addCell(a17);
                writableSheet.addCell(a18);
                writableSheet.addCell(a19);
                writableSheet.addCell(a20);
                writableSheet.addCell(a21);
                writableSheet.addCell(a22);
                writableSheet.addCell(a23);
                writableSheet.addCell(a24);
                writableSheet.addCell(a25);
                writableSheet.addCell(a26);
                writableSheet.addCell(a27);
                writableSheet.addCell(a28);
                writableSheet.addCell(a29);
                writableSheet.addCell(a30);
                writableSheet.addCell(a31);
                writableSheet.addCell(a32);
                writableSheet.addCell(a33);
                writableSheet.addCell(a34);
                writableSheet.addCell(a35);
                writableSheet.addCell(a36);
                writableSheet.addCell(a37);
                writableSheet.addCell(a38);
                writableSheet.addCell(a39);
                writableSheet.addCell(a40);
                writableSheet.addCell(a41);
                writableSheet.addCell(a42);
                writableSheet.addCell(a43);
                writableSheet.addCell(a44);
                writableSheet.addCell(a45);
                writableSheet.addCell(a46);
                writableSheet.addCell(a47);
                writableSheet.addCell(a48);
                writableSheet.addCell(a49);
                writableSheet.addCell(a50);
                writableSheet.addCell(a51);
                writableSheet.addCell(a52);
                writableSheet.addCell(a53);
                writableSheet.addCell(a54);
                writableSheet.addCell(a55);
                writableSheet.addCell(a56);
                writableSheet.addCell(a57);
            }
            writableWorkbook.write();
            writableWorkbook.close();
        } catch (WriteException e) {
            e.printStackTrace();
        }

    }
}
