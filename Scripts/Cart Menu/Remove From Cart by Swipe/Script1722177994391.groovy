import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
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
import com.swaglabsmobileapp.utils.Utils

import internal.GlobalVariable

import org.assertj.core.api.NumberAssert
import org.openqa.selenium.Keys as Keys

if (isRunAlone) {
	Mobile.callTestCase(findTestCase('Test Cases/Cart Menu/Add To Cart'), null)
}
int countSelectedProduct = Integer.parseInt(Mobile.getText(findTestObject('Object Repository/Product List/txtCart'), 5))
KeywordUtil.logInfo('Initial products on cart : '+countSelectedProduct)
Utils utils = new Utils()
for (productName in productNames) {
	KeywordUtil.logInfo("product name : "+productName)
	Mobile.scrollToText(productName)
	//replace following swiped button by the one button with remove tag
	utils.swipe(findTestObject('Object Repository/Cart Page/txtProductName',['productName':productName]))
	
}

for (productName in productNames) {
	
	KeywordUtil.logInfo("product name : "+productName)
	if(utils.verifyElementNotPresentWhileScrolling('Object Repository/Cart Page/txtProductName',['productName':productName],3)) {
		KeywordUtil.markPassed("Product "+productName+" berhasil dihapus")
		countSelectedProduct -= 1
		KeywordUtil.logInfo('Initial products on cart : '+countSelectedProduct)
	}else {
		KeywordUtil.markFailed("Product "+productName+" gagal dihapus")
	}
}
Mobile.verifyElementText(findTestObject('Object Repository/Product List/txtCart'), Integer.toString(countSelectedProduct), FailureHandling.STOP_ON_FAILURE)

if (isContinue) {
	Mobile.scrollToText('CHECKOUT')
	utils.getWhileScroll('//android.view.ViewGroup[@content-desc="test-CHECKOUT"]')
	Mobile.tap(findTestObject('Object Repository/Cart Page/btnCheckout'), 0)
}else {
	
}
