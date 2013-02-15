/*
 * Copyright (C) 2013 The Cat Hive Developers.
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


package com.cathive.fx.guice.example.calculator;

import java.util.ResourceBundle;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * @author Benjamin P. Jung
 */
public final class CalculatorAppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ResourceBundle.class).annotatedWith(Names.named("resources"))
            .toInstance(ResourceBundle.getBundle(CalculatorApp.class.getName()));
    }

}