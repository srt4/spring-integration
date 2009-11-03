/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.integration.osgi.config.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.channel.SubscribableChannel;
import org.springframework.integration.controlbus.ControlBus;
import org.springframework.integration.osgi.AbstractSIConfigBundleTestDeployer;
import org.springframework.integration.osgi.stubs.SIBundleContextStub;
import org.springframework.integration.util.ErrorHandlingTaskExecutor;


/**
 * @author Oleg Zhurakousky
 * @since 2.0
 */
public class BusConfigParserTests extends AbstractSIConfigBundleTestDeployer {

	@Test
	public void testDefaultControlBusConfig() throws Exception {
		BundleContext bundleContext = SIBundleContextStub.getInstance();
		ApplicationContext ac = this.deploySIConfig(bundleContext, 
				                                    "org/springframework/integration/osgi/config/xml/", 
				                                    "BusConfigParserTests-default.xml");
		ControlBus controlBus = (ControlBus) ac.getBean("controlBus-DEFAULT_CONTROL_GROUP");
		assertNotNull(controlBus);
		ServiceReference[] sr = bundleContext.getServiceReferences(ControlBus.class.getName(), 
				                                                  "(&(org.springframework.osgi.bean.name=controlBus-DEFAULT_CONTROL_GROUP))");
		assertNotNull(sr);
		assertTrue(sr.length == 1);
		controlBus = (ControlBus) bundleContext.getService(sr[0]);
	}
	
	@Test
	public void testNamededControlBusConfig() throws Exception {
		BundleContext bundleContext = SIBundleContextStub.getInstance();
		ApplicationContext ac = this.deploySIConfig(bundleContext, 
				                                    "org/springframework/integration/osgi/config/xml/", 
				                                    "BusConfigParserTests-overrideGroupName.xml");
		ControlBus controlBus = (ControlBus) ac.getBean("controlBus-FOO");
		assertNotNull(controlBus);
		ServiceReference sr = bundleContext.getServiceReference(ControlBus.class.getName());
		assertNotNull(sr);
		controlBus = (ControlBus) bundleContext.getService(sr);
	}
}
