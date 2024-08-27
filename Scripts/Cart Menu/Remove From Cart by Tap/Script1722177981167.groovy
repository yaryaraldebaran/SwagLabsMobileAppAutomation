import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.swaglabsmobileapp.utils.Utils
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
Utils util = new Utils()
if (isRunAlone) {
	Mobile.callTestCase(findTestCase('Test Cases/Cart Menu/Add To Cart'), null)
}
for (productName in productNames) {
	KeywordUtil.logInfo("product name : "+productName)
	Mobile.scrollToText(productName)
	//replace following button by the one button with remove tag

}
int countActualProducts = Integer.parseInt(Mobile.getText(findTestObject('Object Repository/Product List/txtCart'), 10)) 
int countSelectedProduct = 0
for (productName in productNames) {
	
	KeywordUtil.logInfo("product name : "+productName)
	Mobile.scrollToText(productName)
	if (removePage == 'cart') {
		List strRemove = util.getWhileScroll(String.format("//android.view.ViewGroup[@content-desc='test-REMOVE' and ancestor::*/*/*[@text='%s']]",productName))
		countSelectedProduct = strRemove.size()
//		if (Mobile.verifyElementExist(findTestObject('Object Repository/Cart Page/btnRemoveProduct',['productName':productName]), 10, FailureHandling.CONTINUE_ON_FAILURE)) {
//			countSelectedProduct++
//		}
	}else {
		if (Mobile.verifyElementExist(findTestObject('Object Repository/Product List/btnRemove',['productName':productName]), 10, FailureHandling.CONTINUE_ON_FAILURE)) {
			countSelectedProduct++
		}
	}

}
int countRemoved = countActualProducts - countSelectedProduct

Mobile.verifyElementText(findTestObject('Object Repository/Product List/txtCart'), Integer.toString(countRemoved), FailureHandling.STOP_ON_FAILURE)
Mobile.tap(findTestObject('Object Repository/Product List/btnCart'), 5)
