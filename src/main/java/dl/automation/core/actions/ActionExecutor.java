/**
 *
 */
package dl.automation.core.actions;

import dl.automation.core.exceptions.AssertFailedException;
import dl.automation.core.executor.IDLWebdriver;
import dl.automation.core.interfaces.IExecutionContext;
import dl.automation.core.model.TestDataObjectModel;
import dl.automation.core.utils.Log4jUtil;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public final class ActionExecutor {
	static Logger log = Log4jUtil.loadLogger(ActionExecutor.class);
	static BaseActions oldActions = new OldActions();
	public static int dataset;

	public static void execute(IDLWebdriver idlWebdriver, IExecutionContext context, List<WebElement> elements,
			String action, TestDataObjectModel data) throws Exception {

		if (data != null) {
			int size = data.getDataValues().size();
			if (dataset > size) {
				dataset = 1;
			}
		}

		WebDriver webDriver = idlWebdriver.getWebDriver();
		// log.info("Current Action to be executed on web Element is ====>>> " +
		// action.toLowerCase());

		switch (action.toLowerCase()) {
		case "click":
			oldActions.click(webDriver, elements.get(0));
			break;

		case "jsclick":
			oldActions.jsclick(webDriver, elements.get(0));
			break;

		case "jsenter":
			oldActions.jsEnter(webDriver, context, elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "mouseover":
			oldActions.mouseOver(webDriver, elements.get(0));
			break;

		case "clearenter":
			oldActions.clearAndEnterInputValue(webDriver, context, elements.get(0),
					data.getDataValues().get(dataset - 1));
			break;

		case "refresh":
			oldActions.refreshBrowser(webDriver);
			break;

		case "navigateurl":
			oldActions.navigateUrl(webDriver, data.getDataValues().get(1));
			break;

		case "clear":
			oldActions.clear(elements.get(0));
			break;

		case "todaydate":
			oldActions.todayDate(webDriver, elements.get(0));
			break;

		case "futuredate":
			oldActions.futureDate(webDriver, elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "multiclick":
			oldActions.multiClick(elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "selectframe":
			oldActions.selectFrame(webDriver, elements.get(0));
			break;

		case "verifycolor":
			oldActions.VerifyAttributeColor(webDriver, elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "actionsclick":
			oldActions.actionsclick(webDriver, elements.get(0));
			break;

		case "actionsdoubleclick":
			oldActions.actionsDoubleClick(webDriver, elements.get(0));
			break;
		case "actionsdragndrop":
			oldActions.ActionsDragNDrop(webDriver, elements.get(0), data.getDataValues().get(dataset - 1));
			break;
		case "actionsenter":
			oldActions.actionsenter(webDriver, elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "clickandwait":
			oldActions.clickandwait(elements.get(0));
			break;

		case "isdisabled":
			oldActions.isdisabled(elements.get(0));
			break;

		case "iselementdisplayed":
			oldActions.iselementdisplayed(elements.get(0));
			break;

		case "iselementnotdisplayed":
			oldActions.iselementnotdisplayed(webDriver, data.getDataValues().get(dataset - 1));
			break;

		case "isenabled":
			oldActions.isenabled(elements.get(0));
			break;

		case "isselected":
			oldActions.isselected(elements.get(0));
			break;

		case "check":
			oldActions.check(webDriver, elements.get(0));
			break;

		case "uncheck":
			oldActions.uncheck(elements.get(0));
			break;

		case "alertaccept":
			oldActions.AlertAccept(webDriver);
			break;

		case "alertdismiss":
			oldActions.AlertDismiss(webDriver);
			break;

		case "waitforelement":
			oldActions.waitForElement(webDriver, elements.get(0));
			break;

		case "waittime":
			oldActions.waittime(data.getDataValues().get(dataset - 1));
			break;

		case "mousemove":
			oldActions.mousemove();
			break;

		case "defaultselectedvalue":
			oldActions.defaultSelectedValue(context, elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "scrolltoviewattention":
			oldActions.scrollToViewAttention(webDriver, elements.get(0));
			break;

		case "currentwindowclose":
			oldActions.currentWindowClose(webDriver);
			break;

		case "switchtodefault":
			oldActions.switchTodefault(webDriver);
			break;

		case "deletecookies":
			oldActions.deleteCookies(webDriver);
			break;

		case "fileupload":
			oldActions.uploadFile(webDriver, data.getDataValues().get(dataset - 1));
			break;

		case "selectnewwindow":
			oldActions.selectWindow(webDriver, data.getDataValues().get(dataset - 1));
			break;

		case "verifynotpresent":
			oldActions.verifynotpresent(webDriver, elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "verifyobject":
			oldActions.verifyobject(elements.get(0));
			break;

		case "verify":
			oldActions.verify(elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "verifycontains":
			oldActions.verifycontains(elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "select":
			oldActions.selectdropdown(webDriver, context, elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "randomstring":
			oldActions.randomString(webDriver, elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "generatenpi":
			oldActions.generateNPI(webDriver, elements.get(0));
			break;

		case "getcount":
			oldActions.getCount(webDriver, elements, context, data);
			break;

		case "quit":
			oldActions.quit(idlWebdriver);
			break;

		case "emailverification":
			oldActions.emailverification(webDriver);
			break;

		case "listverify":
			oldActions.listverify(webDriver, elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "navigate":
			((OldActions) oldActions).navigate(idlWebdriver, data);
			break;

		case "pagedown":
			oldActions.PageDown(webDriver);
			break;

		case "pageup":
			oldActions.pageup(webDriver);
			break;

		case "enterkey":
			oldActions.Desk_Enter();
			break;
		case "replacetext":
			oldActions.Replace_text(webDriver, elements.get(0), data.getDataValues().get(dataset - 1));
			break;
		case "tabclick":
			oldActions.Robot_Tab();
			break;
		case "textentry":
			oldActions.RobotTextEntry(data.getDataValues().get(dataset - 1));
			break;
		case "escclick":
			oldActions.robot_esc();
			break;

		case "deleteclient":
			oldActions.deleteClient(webDriver, elements.get(0));
			break;

		case "backspace":
			oldActions.Robot_BackSpace();
			break;

		case "downarrow":
			oldActions.Robot_DownArrow();
			break;

		case "selectbyvalue":
			oldActions.Select_ByValue(webDriver, elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "time":
			oldActions.Time(webDriver, elements.get(0));
			break;

		case "verifyvalue":
			oldActions.verifyvalue(webDriver, elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "clicktablerow":
			oldActions.clicktablerow(webDriver, data.getDataValues().get(dataset - 1));
			break;

		case "clientnamecheck":
			oldActions.clientnamecheck(webDriver, elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "selectrecord":
			oldActions.select_autofill(elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "verifyradioselection":
			oldActions.verifyRadioSelection(elements.get(0));
			break;

		case "selectdate":
			oldActions.SelectDate(webDriver, elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "verifydate":
			oldActions.verifyDate(elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "checkboxstatus":
			oldActions.CheckBoxStatus(elements.get(0), data.getDataValues().get(dataset - 1));
			break;
		case "currentdatecheck":
			oldActions.currentDate(webDriver, elements.get(0));
			break;

		case "facilitydeleteclick":
			oldActions.deletefacility(webDriver, elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "servicefacilityroomclick":
			oldActions.servicefacilityroomclick(elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "getvalue":
			oldActions.getText(elements.get(0));
			break;

		case "setvalue":
			oldActions.setText(elements.get(0));
			break;

		case "ediverification":
			oldActions.EDIFileContains(webDriver, data.getDataValues().get(dataset - 1));
			break;

		case "verifyclick":
			oldActions.verifyclick(webDriver, data.getDataValues().get(dataset - 1));
			break;

		case "calenderdayclick":
			oldActions.dayclick(webDriver, elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "weeklypatterncheck":
			oldActions.weeklypatterncheck(webDriver, elements.get(0));
			break;

		case "linkexistancecheck":
			oldActions.linkexistancecheck(webDriver, data.getDataValues().get(dataset - 1));
			break;

		case "deleteclientlist":
			oldActions.delete_client_provider(webDriver, data.getDataValues().get(dataset - 1));
			break;

		case "alerttextverify":
			oldActions.alerttextverify(webDriver, data.getDataValues().get(dataset - 1));
			break;

		case "listelementsthingstodo":
			oldActions.ListOfElements_ThingsToDo(webDriver, elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "removetemplate":
			oldActions.RemoveTemplate(webDriver, data.getDataValues().get(dataset - 1));
			break;

		case "edittemplate":
			oldActions.editTemplate(webDriver, data.getDataValues().get(dataset - 1));
			break;

		case "verifyanddeletetemplate":
			oldActions.verifyAndDeleteTemplate(webDriver, data.getDataValues().get(dataset - 1));
			break;

		case "integrationkeytext":
			oldActions.IntegrationKeyText(webDriver, elements.get(0));
			break;

		case "sqlquery":
			oldActions.runnableSqlQuery(webDriver, data.getDataValues().get(dataset - 1));
			break;

		case "rightclick":
			oldActions.actionsrightclick(webDriver, elements.get(0));
			break;

		case "templatestoreverify":
			oldActions.TemplateStoreVerify(webDriver, elements.get(0));
			break;

		case "verifyobjectnotfound":
			oldActions.verifyobjectnotfound(elements.get(0));
			break;

		case "checkgreater":
			oldActions.checkgreater(elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "verifypdf":
			oldActions.Pdf_Read(webDriver, data.getDataValues().get(dataset - 1));
			break;

		case "insuranceclientlistcheck":
			oldActions.Insuranceclientlistcheck(elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "clientbalchrgslistofelements":
			oldActions.clientbalancestatementcharges_listofelements(webDriver, elements.get(0),
					data.getDataValues().get(dataset - 1));
			break;

		case "waitfortext":
			oldActions.waitfortransmissionsuccessfull(webDriver, data.getDataValues().get(dataset - 1));
			break;

		case "enterdate":
			oldActions.enterDate(webDriver, elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "insurancelistverify":
			oldActions.insurancelistverify(webDriver, elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "verifynull":
			oldActions.verifynullvalue(webDriver, elements.get(0));
			break;

		case "uparrow":
			oldActions.Robot_UpArrow();
			break;

		case "loginmessage":
			oldActions.loginMessage(webDriver);
			break;

		case "loadbundlelist":
			oldActions.loadbundles_List(webDriver, elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "loadbundleslistselect":
			oldActions.loadbundleselect(webDriver, elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "edifilecontentverify":
			oldActions.EDIfilecontentverify(webDriver, elements.get(0));
			break;

		case "webptfinancialcheck":
			oldActions.table_transmission(webDriver, elements.get(0));
			break;

		case "waitformail":
			oldActions.waitformail(webDriver, elements.get(0));
			break;

		case "checkwordcount":
			oldActions.checkwordcount(elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "caldayclick":
			oldActions.CalDayClick(webDriver, elements.get(0), data.getDataValues().get(dataset - 1));
			break;

		case "weekdaypicker":
			oldActions.weekdaypicker(webDriver, elements.get(0));
			break;

		case "randomnumber":
			oldActions.randomnumber(elements.get(0));
			break;

		case "loginmessageclose":
			oldActions.Announcements_Close(webDriver);
			break;

		case "cronjob":
			oldActions.configurablewaitmethod(data.getDataValues().get(dataset - 1));
			break;
		case "selectprovider":
			oldActions.selectProvider(webDriver, data.getDataValues().get(dataset - 1));
			break;

		case "paymentclasses":
			oldActions.Paymentclass_Delete(webDriver, elements.get(0), data.getDataValues().get(dataset - 1));
			break;
			
		case "deletemails":
			oldActions.Delete_Emails(webDriver, data.getDataValues().get(dataset - 1));
			break;
			
		case "alertispresent":
			oldActions.alertIsPresent(webDriver);
			break;

		case "verifylist":
			oldActions.verifyList(webDriver,elements.get(0));
			break;

		default:
			log.info("There is no keyword of text ==>  " + action.toLowerCase());
			throw new AssertFailedException("\"" + action + "\"" + " Keyword not defined yet");

		}

	}
}
