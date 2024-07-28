import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.time.Duration

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.windows.keyword.builtin.SwitchToWindowKeyword
import com.swaglabsmobileapp.utils.Utils

import internal.GlobalVariable
import io.appium.java_client.MobileElement
import io.appium.java_client.TouchAction
import io.appium.java_client.touch.WaitOptions
import io.appium.java_client.touch.offset.PointOption

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import org.openqa.selenium.WebElement as Keys

Utils util = new Utils()
Mobile.callTestCase(findTestCase('Test Cases/Login Menu/Login'), null)
Mobile.tap(findTestObject('Object Repository/Product List/btnSortProduct'),10)

String xpathName = "//android.widget.TextView[@content-desc='test-Item title']"
String xpathPrice = "//android.widget.TextView[@content-desc='test-Price']"

if ( sortBy =='name' && order =='ascending') {
	Mobile.tap(findTestObject('Object Repository/Product List/btnSortBy',['sortby':'Name (A to Z)']), 5)
	Mobile.tap(findTestObject('Object Repository/Product List/btnToggleView'),5)
	List<String> prodName = util.getWhileScroll(xpathName)
	if (util.determineStringOrder(prodName).equals("ascending")) {
		KeywordUtil.markPassed("Product name sorted ascending")
	}else {
		KeywordUtil.markFailed("Product name not sorted ascending")
	}

}
else if ( sortBy =='name' && order =='descending') {
	Mobile.tap(findTestObject('Object Repository/Product List/btnSortBy',['sortby':'Name (Z to A)']), 5)
	Mobile.tap(findTestObject('Object Repository/Product List/btnToggleView'),5)
	List<String> prodName = util.getWhileScroll(xpathName)
	if (util.determineStringOrder(prodName).equals("descending")) {
		KeywordUtil.markPassed("Product name sorted descending")
	}else {
		KeywordUtil.markFailed("Product name not sorted descending")
	}
}
else if ( sortBy =='price' && order =='ascending') {
	Mobile.tap(findTestObject('Object Repository/Product List/btnSortBy',['sortby':'Price (low to high)']), 5)
	Mobile.tap(findTestObject('Object Repository/Product List/btnToggleView'),5)
	List<String> strProdPrice = util.getWhileScroll(xpathPrice)
	
	List<Float> prodPrice = new ArrayList<>()
	for(int i =0; i<strProdPrice.size();i++) {
		prodPrice.add(i, util.convertCurrencyStringToFloat(strProdPrice[i])) 
	}
	KeywordUtil.logInfo("pricelist : "+prodPrice)
	if (util.determineFloatsOrder(prodPrice).equals("ascending")) {
		KeywordUtil.markPassed("Price sorted ascending")
	}else {
		KeywordUtil.markFailed("Price not sorted ascending")
	}
}
else if ( sortBy =='price' && order =='descending') {
	Mobile.tap(findTestObject('Object Repository/Product List/btnSortBy',['sortby':'Price (high to low)']), 5)
	Mobile.tap(findTestObject('Object Repository/Product List/btnToggleView'),5)
	List<String> strProdPrice = util.getWhileScroll(xpathPrice)
	
	List<Float> prodPrice = new ArrayList<>()
	for(int i =0; i<strProdPrice.size();i++) {
		prodPrice.add(i, util.convertCurrencyStringToFloat(strProdPrice[i]))
	}
	KeywordUtil.logInfo("pricelist : "+prodPrice)
	if (util.determineFloatsOrder(prodPrice).equals("descending")) {
		KeywordUtil.markPassed("Price sorted descending")
	}else {
		KeywordUtil.markFailed("Price not sorted descending")
	}

}
else {
	KeywordUtil.markWarning("Please check your order parameter")
}
Mobile.tap(findTestObject('Object Repository/General/btnMenu'), 3)
Mobile.tap(findTestObject('Object Repository/General/btnLogout'), 3)
