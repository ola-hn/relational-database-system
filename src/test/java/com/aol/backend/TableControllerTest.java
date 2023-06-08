package com.aol.backend;

import com.aol.backend.controllers.TableController;
import com.aol.backend.network.Node;
import com.aol.models.Query;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import io.vertx.ext.web.RoutingContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class TableControllerTest {

  private TableController tableController;

  @BeforeEach
  public void setup() {
    HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
    Node.setHazelcastInstance(hazelcastInstance);
    tableController = new TableController();
  }

  @Test
  public void testCreateTable() {
    RoutingContext context = mock(RoutingContext.class);
    when(context.pathParam("name")).thenReturn("female_coaches");

    String jsonTable = "{\"name\":\"female_coaches\",\"columns\":[{\"name\":\"coach_id\",\"type\":\"integer\"},{\"name\":\"coach_url\",\"type\":\"string\"},{\"name\":\"short_name\",\"type\":\"string\"},{\"name\":\"long_name\",\"type\":\"string\"},{\"name\":\"dob\",\"type\":\"string\"},{\"name\":\"nationality_id\",\"type\":\"integer\"},{\"name\":\"nationality_name\",\"type\":\"string\"},{\"name\":\"face_url\",\"type\":\"string\"}]}";
    when(context.getBodyAsString()).thenReturn(jsonTable);

    tableController.createTable(context);

    // Verify the response
    verify(context.response()).setStatusCode(201);
    verify(context.response()).end("Table successfully created!\n\n" );
  }

  @Test
  public void testDropTable() {

    RoutingContext context = mock(RoutingContext.class);
    when(context.pathParam("name")).thenReturn("female_coaches");


    tableController.dropTable(context);


    verify(context.response()).setStatusCode(202);
    verify(context.response()).end("Table successfully dropped!");
  }

  @Test
  public void testSelectQuery() {
    RoutingContext context = mock(RoutingContext.class);
    when(context.getBodyAsString()).thenReturn("{\"tableName\":\"female_coaches\",\"columnNames\":[\"*\"],\"conditions\":[],\"groupByColumns\":[],\"count\":\"*\",\"sum\":\"\"}");

    StringBuilder sb = new StringBuilder();

    sb.append("SELECT [*] COUNT(*)\n")
      .append("FROM female_coaches\n\n")
      .append("[*]\n")
      .append("3.726525E7 ;/coach/37265250/thorsteinn-halldorsson/ ;T. Halldórsson ;Thorsteinn Halldórsson ; ;24.0 ;Iceland ; ;\n")
      .append("3.7296295E7 ;/coach/37296295/pedro-martinez-losa/ ;P. Martínez Losa ;Pedro Martínez Losa ; ;45.0 ;Spain ; ;\n")
      .append("3.7296301E7 ;/coach/37296301/amandine-miquel/ ;A. Miquel ;Amandine Miquel ;1984-08-04 ;18.0 ;France ; ;\n")
      .append("3.7303727E7 ;/coach/37303727/tony-gustavsson/ ;T. Gustavsson ;Tony Gustavsson ;1973-08-14 ;46.0 ;Sweden ; ;\n")
      .append("3.734291E7 ;/coach/37342910/lydia-bedford/ ;L. Bedford ;Lydia Bedford ; ;14.0 ;England ; ;\n")
      .append("3.734754E7 ;/coach/37347540/futoshi-ikeda/ ;F. Ikeda ;Futoshi Ikeda ;1970-10-04 ;163.0 ;Japan ; ;\n")
      .append("3.7350128E7 ;/coach/37350128/ives-serneels/ ;I. Serneels ;Ives Serneels ;1972-10-16 ;7.0 ;Belgium ; ;\n")
      .append("3.7350129E7 ;/coach/37350129/vlatko-andonovski/ ;V. Andonovski ;Vlatko Andonovski ;1976-09-14 ;95.0 ;United States ; ;\n")
      .append("3.73578E7 ;/coach/37357800/hege-riise/ ;H. Riise ;Hege Riise ;1969-07-18 ;36.0 ;Norway ; ;\n")
      .append("3.7375752E7 ;/coach/37375752/colin-bell/ ;C. Bell ;Colin Bell ;1961-08-05 ;14.0 ;England ; ;\n")
      .append("3.7416863E7 ;/coach/37416863/naruephon-kaenson/ ;N.Kaenson ;Naruephon Kaenson ; ;188.0 ;Thailand ; ;\n")
      .append("3.7428117E7 ;/coach/37428117/beverly-priestman/ ;B. Priestman ;Beverly Priestman ; ;70.0 ;Canada ; ;\n")
      .append("3.7430277E7 ;/coach/37430277/pedro-lopez/ ;Pedro López ;Pedro López ;1979-05-06 ;45.0 ;Spain ; ;\n")
      .append("3.7469773E7 ;/coach/37469773/pia-mariane-sundhage/ ;P. Sundhage ;Pia Mariane Sundhage ;1960-02-13 ;46.0 ;Sweden ; ;\n")
      .append("3.7524025E7 ;/coach/37524025/marc-skinner/ ;M. Skinner ;Marc Skinner ; ;14.0 ;England ; ;\n")
      .append("3.7524186E7 ;/coach/37524186/rehanne-skinner/ ;R. Skinner ;Rehanne Skinner ;1979-11-13 ;14.0 ;England ; ;\n")
      .append("3.7524489E7 ;/coach/37524489/jitka-klimkova/ ;J. Klimkova ;Jitka Klimkova ;1974-08-20 ;12.0 ;Czech Republic ; ;\n")
      .append("3.7554044E7 ;/coach/37554044/olli-harder/ ;O. Harder ;Olli Harder ; ;198.0 ;New Zealand ; ;\n")
      .append("3.7573961E7 ;/coach/37573961/randy-waldrum/ ;R. Waldrum ;Randy Waldrum ; ;95.0 ;United States ; ;\n")
      .append("3.7573962E7 ;/coach/37573962/hubert-busby/ ;H. Busby ;Hubert Busby ;1969-06-18 ;70.0 ;Canada ; ;\n")
      .append("3.7578659E7 ;/coach/37578659/christophe-forest/ ;C. Forest ;Christophe Forest ;1969-03-18 ;18.0 ;France ; ;\n")
      .append("3.7612238E7 ;/coach/37612238/german-dario-portanova/ ;G. Portanova ;Germán Darío Portanova ;1973-10-19 ;52.0 ;Argentina ; ;\n")
      .append("3.7614418E7 ;/coach/37614418/qingxia-shui/ ;Q. Shui ;Qingxia Shui ;1966-12-18 ;155.0 ;China PR ; ;\n")
      .append("3.7615657E7 ;/coach/37615657/frederic-goncalves/ ;F. Gonçalves ;Frédéric Gonçalves ;1982-03-06 ;18.0 ;France ; ;\n")
      .append("3.7617776E7 ;/coach/37617776/mathieu-rufie/ ;M. Rufié ;Mathieu Rufié ;1983-06-17 ;18.0 ;France ; ;\n")
      .append("3.7654887E7 ;/coach/37654887/gerard-precheur/ ;G. Prêcheur ;Gérard Prêcheur ;1959-10-23 ;18.0 ;France ; ;\n")
      .append("3.7667852E7 ;/coach/37667852/michel-bradaia/ ;M. Bradaia ;Michel Bradaia ;1971-12-03 ;18.0 ;France ; ;\n");
    tableController.selectQuery(context);

    verify(context.response()).setStatusCode(200);
    verify(context.response()).end(sb.toString());
  }


  @Test
  public void testDeleteFromTable() {
    RoutingContext context = mock(RoutingContext.class);
    when(context.getBodyAsString()).thenReturn("{\"tableName\":\"female_coaches\",\"columnNames\":[\"nationality_name\"],\"conditions\":[\"coach_id=2079\"],\"groupByColumns\":[],\"count\":\"*\",\"sum\":\"\"}");

    tableController.deleteFromTable(context);

    verify(context.response()).setStatusCode(200);
    verify(context.response()).end("Successfully deleted from table!");
  }


  @Test
  public void testGetTable() {

    StringBuilder sb = new StringBuilder();

    sb.append("TABLE: female_coaches`\n\n");
    sb.append("COLUMNS");
    sb.append("[COLUMN: name: coach_id type: integer , COLUMN: name: coach_url type: string , COLUMN: name: short_name type: string , COLUMN: name: long_name type: string , COLUMN: name: dob type: string , COLUMN: name: nationality_id type: integer , COLUMN: name: nationality_name type: string , COLUMN: name: face_url type: string ]\n\n");
    sb.append("27 rows");


    RoutingContext context = mock(RoutingContext.class);
    when(context.pathParam("name")).thenReturn("female_coaches");

    tableController.getTable(context);

    verify(context.response()).setStatusCode(200);
    verify(context.response()).end(sb.toString());
  }
/*
  @Test
  public void testShowTables() {
    RoutingContext context = mock(RoutingContext.class);

    tableController.showTables(context);

    verify(context.response()).setStatusCode(201);
    verify(context.response()).end(sb.toString());
  }

  @Test
  public void testDropTables() {
    RoutingContext context = mock(RoutingContext.class);

    tableController.dropTables(context);

    // Verify the response
    verify(context.response()).setStatusCode(201);
    verify(context.response()).end("All tables successfully dropped");
  }

  @Test
  public void testInsertToTable() {
    RoutingContext context = mock(RoutingContext.class);
    when(context.pathParam("name")).thenReturn("female_coaches");

    String csvContent = "37265250 ;/coach/37265250/thorsteinn-halldorsson/ ;T. Halldórsson ;Thorsteinn Halldórsson ; ;24 ;Iceland ; ;\n" +
      "37296295 ;/coach/37296295/pedro-martinez-losa/ ;P. Martínez Losa ;Pedro Martínez Losa ; ;45 ;Spain ; ;\n" +
      "37296301 ;/coach/37296301/amandine-miquel/ ;A. Miquel ;Amandine Miquel ;1984-08-04 ;18 ;France ; ;\n" +
      "37303727 ;/coach/37303727/tony-gustavsson/ ;T. Gustavsson ;Tony Gustavsson ;1973-08-14 ;46 ;Sweden ; ;\n" +
      "37342910 ;/coach/37342910/lydia-bedford/ ;L. Bedford ;Lydia Bedford ; ;14 ;England ; ;\n" +
      "37347540 ;/coach/37347540/futoshi-ikeda/ ;F. Ikeda ;Futoshi Ikeda ;1970-10-04 ;163 ;Japan ; ;\n" +
      "37350128 ;/coach/37350128/ives-serneels/ ;I. Serneels ;Ives Serneels ;1972-10-16 ;7 ;Belgium ; ;\n" +
      "37350129 ;/coach/37350129/vlatko-andonovski/ ;V. Andonovski ;Vlatko Andonovski ;1976-09-14 ;95 ;United States ; ;\n" +
      "37357800 ;/coach/37357800/hege-riise/ ;H. Riise ;Hege Riise ;1969-07-18 ;36 ;Norway ; ;\n" +
      "37375752 ;/coach/37375752/colin-bell/ ;C. Bell ;Colin Bell ;1961-08-05 ;14 ;England ; ;\n" +
      "37416863 ;/coach/37416863/naruephon-kaenson/ ;N.Kaenson ;Naruephon Kaenson ; ;188 ;Thailand ; ;\n" +
      "37428117 ;/coach/37428117/beverly-priestman/ ;B. Priestman ;Beverly Priestman ; ;70 ;Canada ; ;\n" +
      "37430277 ;/coach/37430277/pedro-lopez/ ;Pedro López ;Pedro López ;1979-05-06 ;45 ;Spain ; ;\n" +
      "37469773 ;/coach/37469773/pia-mariane-sundhage/ ;P. Sundhage ;Pia Mariane Sundhage ;1960-02-13 ;46 ;Sweden ; ;\n" +
      "37524025 ;/coach/37524025/marc-skinner/ ;M. Skinner ;Marc Skinner ; ;14 ;England ; ;\n" +
      "37524186 ;/coach/37524186/rehanne-skinner/ ;R. Skinner ;Rehanne Skinner ;1979-11-13 ;14 ;England ; ;\n" +
      "37524489 ;/coach/37524489/jitka-klimkova/ ;J. Klimkova ;Jitka Klimkova ;1974-08-20 ;12 ;Czech Republic ; ;\n" +
      "37554044 ;/coach/37554044/olli-harder/ ;O. Harder ;Olli Harder ; ;198 ;New Zealand ; ;\n" +
      "37573961 ;/coach/37573961/randy-waldrum/ ;R. Waldrum ;Randy Waldrum ; ;95 ;United States ; ;\n" +
      "37573962 ;/coach/37573962/hubert-busby/ ;H. Busby ;Hubert Busby ;1969-06-18 ;70 ;Canada ; ;\n" +
      "37578659 ;/coach/37578659/christophe-forest/ ;C. Forest ;Christophe Forest ;1969-03-18 ;18 ;France ; ;\n" +
      "37612238 ;/coach/37612238/german-dario-portanova/ ;G. Portanova ;Germán Darío Portanova ;1973-10-19 ;52 ;Argentina ; ;\n" +
      "37614418 ;/coach/37614418/qingxia-shui/ ;Q. Shui ;Qingxia Shui ;1966-12-18 ;155 ;China PR ; ;\n" +
      "37615657 ;/coach/37615657/frederic-goncalves/ ;F. Gonçalves ;Frédéric Gonçalves ;1982-03-06 ;18 ;France ; ;\n" +
      "37617776 ;/coach/37617776/mathieu-rufie/ ;M. Rufié ;Mathieu Rufié ;1983-06-17 ;18 ;France ; ;\n" +
      "37654887 ;/coach/37654887/gerard-precheur/ ;G. Prêcheur ;Gérard Prêcheur ;1959-10-23 ;18 ;France ; ;\n" +
      "37667852 ;/coach/37667852/michel-bradaia/ ;M. Bradaia ;Michel Bradaia ;1971-12-03 ;18 ;France ; ;";

    when(context.getBodyAsString()).thenReturn(csvContent);

    tableController.insertToTable(context);

    verify(context.response()).setStatusCode(200);
    verify(context.response()).end("Insertion completed!\n\n" + table.showRows(table.getRows()));
  }
  */

}
