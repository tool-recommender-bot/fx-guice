/*
 * Copyright (C) 2012 The Cat Hive Developers.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cathive.fx.guice;

import static org.testng.Assert.*;

import java.util.ResourceBundle;

import javafx.scene.layout.AnchorPane;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cathive.fx.guice.example.ExamplePaneController;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

@Test
public class GuiceFXMLLoaderTest {

	// Initialized prior to all test methods!
	private Injector injector = null;

	private GuiceFXMLLoader fxmlLoader = null;
	private  ExamplePaneController ctrl;

	@BeforeClass
	private void initialize() {
		final Module module = new AbstractModule() {
			@Override
			protected void configure() {
				bind(GuiceFXMLLoader.class);
			}
		};
		this.injector = Guice.createInjector(module); 
	}
	
	@Test(groups = "stage1")
	public void instantiationTest() throws Exception {
		// Creates an instance of the GuiceFXMLLoader using a Guice Injector
		fxmlLoader = injector.getInstance(GuiceFXMLLoader.class);
		assertNotNull(fxmlLoader);
	}

	@Test(groups = "stage2", dependsOnGroups = "stage1")
	public void fxmlLoadingTest() throws Exception {

		// Fetches an instance of the ExamplePaneController and makes sure it is not (yet) initialized.
		ctrl = injector.getInstance(ExamplePaneController.class);
		assertNotNull(ctrl);
		assertTrue(ctrl.isInjected());
		assertFalse(ctrl.isInitialized());
		assertNull(ctrl.getRootPane());
		assertTrue(ctrl.getMethodCalls().contains("postConstruct()"));
		assertEquals(ctrl.getMethodCalls().size(), 1);

		// Load the FXML file and check that afterwards the controller must be initialized.
		final AnchorPane pane = fxmlLoader.load(getClass().getResource("/ExamplePane.fxml"), ResourceBundle.getBundle("ExamplePane"));
		assertTrue(ctrl.isInitialized());
		assertNotNull(ctrl.getRootPane());
		assertEquals(ctrl.getRootPane(), pane);
		assertTrue(ctrl.getMethodCalls().contains("initialize(java.net.URL, java.util.ResourceBundle)"));
		assertEquals(ctrl.getMethodCalls().size(), 2);

	}

}
