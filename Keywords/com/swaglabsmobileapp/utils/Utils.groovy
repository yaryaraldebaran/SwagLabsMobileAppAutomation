package com.swaglabsmobileapp.utils
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.time.Duration

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords

import internal.GlobalVariable
import io.appium.java_client.MobileElement
import io.appium.java_client.TouchAction
import io.appium.java_client.touch.WaitOptions
import io.appium.java_client.touch.offset.PointOption

import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.server.handler.FindElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By

import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty

import io.appium.java_client.touch.TapOptions
import io.appium.java_client.touch.offset.PointOption
import io.appium.java_client.touch.WaitOptions
import org.openqa.selenium.Dimension
import org.openqa.selenium.Point

import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil

import com.kms.katalon.core.webui.exception.WebElementNotFoundException


class Utils {
	/**
	 * Check if element present in timeout
	 * @param to Katalon test object
	 * @param timeout time to wait for element to show up
	 * @return true if element present, otherwise false
	 */
	@Keyword
	def isElementPresent_Mobile(TestObject to, int timeout){
		try {
			KeywordUtil.logInfo("Finding element with id:" + to.getObjectId())

			WebElement element = MobileElementCommonHelper.findElement(to, timeout)
			if (element != null) {
				KeywordUtil.markPassed("Object " + to.getObjectId() + " is present")
			}
			return true
		} catch (Exception e) {
			KeywordUtil.markFailed("Object " + to.getObjectId() + " is not present")
		}
		return false;
	}

	/**
	 * Get mobile driver for current session
	 * @return mobile driver for current session
	 */
	@Keyword
	def WebDriver getCurrentSessionMobileDriver() {
		return MobileDriverFactory.getDriver();
	}

	public boolean isSortedAlphabetically(List<String> arr) {
		for (int i = 0; i < arr.size() - 1; i++) {
			if (arr[i].compareTo(arr[i + 1]) > 0) {
				return false
			}
		}
		return true
	}
	public void scrollDown() {
		int screenHeight = MobileDriverFactory.getDriver().manage().window().getSize().getHeight()
		int screenWidth = MobileDriverFactory.getDriver().manage().window().getSize().getWidth()
		int startX = screenWidth / 2
		int startY = (int) (screenHeight * 0.58)
		int endY = (int) (screenHeight * 0.2)

		new TouchAction(MobileDriverFactory.getDriver())
				.press(PointOption.point(startX, startY))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
				.moveTo(PointOption.point(startX, endY))
				.release()
				.perform()
	}

	public void swipe(to) {
		// Find the element
		WebElement element = MobileElementCommonHelper.findElement(to, 10)
		//		MobileElement element =  findTestObject(testObjectRelativeId)
		Point location = element.getLocation()
		Dimension size = element.getSize()

		// Calculate swipe start and end points
		int startX = location.getX() + size.getWidth() - 10 // Swipe start at the right side of the element
		int startY = location.getY() + size.getHeight() / 2 // Vertical center of the element
		int endX = location.getX() + 10 // Swipe end at 10 pixels from the left edge of the screen
		int endY = startY // Same vertical position
		KeywordUtil.logInfo("Point x : "+startX)
		KeywordUtil.logInfo("Point y : "+startY)
		TouchAction touchAction = new TouchAction(MobileDriverFactory.getDriver())
		touchAction.press(PointOption.point(startX, startY))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
				.moveTo(PointOption.point(endX, endY))
				.release()
				.perform()
				
		touchAction.tap(PointOption.point(startX, startY))
				.release()
				.perform()

	}

	public List<String> getWhileScroll(String xpathElm) {
		List<String> allTexts = new ArrayList<>()
		Set<String> seenTexts = new HashSet<>()
		boolean reachedEnd = false
		while (!reachedEnd ) {
			// Find all elements matching the XPath
			List<WebElement> elements = MobileDriverFactory.getDriver().findElements(By.xpath(xpathElm))

			// Collect the text of each element and add to the list
			for (WebElement element : elements) {
				String text = element.getText()
				if (!seenTexts.contains(text)) {
					allTexts.add(text)
					seenTexts.add(text)
				}
			}

			// Scroll down
			scrollDown()

			// Check if the last visible element before scrolling is still the last element after scrolling
			List<WebElement> newElements = MobileDriverFactory.getDriver().findElements(By.xpath(xpathElm))
			if (newElements.size() > 0 && elements.size() > 0) {
				if (newElements.get(newElements.size() - 1).getText().equals(elements.get(elements.size() - 1).getText())) {
					reachedEnd = true
				}
			} else {
				reachedEnd = true
			}
		}
		return allTexts
	}
	
	
	def static boolean verifyElementNotPresentWhileScrolling(String objectPath, Map<String, String> parameters, int maxScrolls) {
		def driver = MobileDriverFactory.getDriver()
		TestObject testObject = new TestObject('DynamicObject')
		testObject.addProperty('xpath', ConditionType.EQUALS, objectPath)

		// Set dynamic parameters
		parameters.each { key, value ->
			testObject.addProperty(key, ConditionType.EQUALS, value)
		}

		boolean isElementNotPresent = true

		for (int i = 0; i < maxScrolls; i++) {
			// Check if the element is not present
			if (!isElementPresent(testObject)) {
				return true  // Element is not present, so return true
			}
			scrollDown()
			// Scroll in the specified direction
			
		}

		// If the maximum scroll attempts are reached and the element is still found, return false
		return !isElementPresent(testObject)
	}

	/**
	 * Check if an element is present.
	 * @param testObject The TestObject to check.
	 * @return True if the element is present; false otherwise.
	 */
	@Keyword
	def static boolean isElementPresent(TestObject testObject) {
		try {
			return MobileDriverFactory.getDriver().findElementByXPath(testObject.getObjectId()) != null
		} catch (Exception e) {
			return false
		}
	}
	
	
	
	public float convertCurrencyStringToFloat(String currencyString) {
		String numericString = currencyString.replace('$', '')
		float floatValue = Float.parseFloat(numericString)
		return floatValue
	}

	String determineStringOrder(List<String> array) {
		if (array.size() < 2) {
			return "random" // Not enough elements to determine order
		}

		boolean isAscending = true
		boolean isDescending = true

		for (int i = 0; i < array.size() - 1; i++) {
			if (array[i].compareTo(array[i + 1]) < 0) {
				isDescending = false
			} else if (array[i].compareTo(array[i + 1]) > 0) {
				isAscending = false
			}
		}

		if (isAscending) {
			return "ascending"
		} else if (isDescending) {
			return "descending"
		} else {
			return "random"
		}
	}

	String determineFloatsOrder(List<Float> array) {
		if (array.size() < 2) {
			return "random" // Not enough elements to determine order
		}

		boolean isAscending = true
		boolean isDescending = true

		for (int i = 0; i < array.size() - 1; i++) {
			if (array[i] < array[i + 1]) {
				isDescending = false
			} else if (array[i] > array[i + 1]) {
				isAscending = false
			}
		}

		if (isAscending) {
			return "ascending"
		} else if (isDescending) {
			return "descending"
		} else {
			return "random"
		}
	}
}