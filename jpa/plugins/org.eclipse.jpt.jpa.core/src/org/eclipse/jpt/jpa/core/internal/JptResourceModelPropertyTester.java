/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceModel;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;

/**
 * Property tester for {@link JptResourceModel}.
 * See <code>org.eclipse.jpt.jpa.core/plugin.xml:org.eclipse.core.expressions.propertyTesters</code>
 */
public class JptResourceModelPropertyTester
	extends PropertyTester
{
	public static final String IS_LATEST_SUPPORTED_VERSION = "isLatestSupportedVersion"; //$NON-NLS-1$
	public static final String IS_NOT_LATEST_SUPPORTED_VERSION = "isNotLatestSupportedVersion"; //$NON-NLS-1$
	public static final String IS_GENERIC_MAPPING_FILE = "isGenericMappingFile"; //$NON-NLS-1$

	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (receiver instanceof JptResourceModel) {
			return this.test((JptResourceModel) receiver, property, expectedValue);
		}
		return false;
	}

	private boolean test(JptResourceModel resourceModel, String property, Object expectedValue) {
		if (property.equals(IS_NOT_LATEST_SUPPORTED_VERSION)) {
			return ! this.test(resourceModel, IS_LATEST_SUPPORTED_VERSION, expectedValue);
		}
		if (property.equals(IS_LATEST_SUPPORTED_VERSION)) {
			boolean expected = (expectedValue == null) ? true : ((Boolean) expectedValue).booleanValue();
			boolean actual = this.isLatestSupportedVersion(resourceModel);
			return actual == expected;
		}
		if (property.equals(IS_GENERIC_MAPPING_FILE)) {
			boolean expected = (expectedValue == null) ? true : ((Boolean) expectedValue).booleanValue();
			boolean actual = this.isGenericMappingFile(resourceModel);
			return actual == expected;
		}
		return false;
	}

	private boolean isLatestSupportedVersion(JptResourceModel resourceModel) {
		JpaProject jpaProject = this.getJpaProject(resourceModel.getFile().getProject());
		if (jpaProject == null) {
			// if we get to this tester, the JPA project should be there;
			// so this will probably never happen
			return true;  // effectively disable "upgrade"
		}
		JptResourceType resourceType = resourceModel.getResourceType();
		if (resourceType == null) {
			return true; // effectively disable "upgrade"
		}
		String latestVersion = jpaProject.getJpaPlatform().getMostRecentSupportedResourceType(resourceType.getContentType()).getVersion();
		return ObjectTools.equals(resourceType.getVersion(), latestVersion);
	}

	private boolean isGenericMappingFile(JptResourceModel resourceModel) {
		JpaProject jpaProject = this.getJpaProject(resourceModel.getFile().getProject());
		if (jpaProject == null) {
			// if we get to this tester, the JPA project should be there;
			// so this will probably never happen
			return true;  // effectively disable "upgrade"
		}
		JptResourceType resourceType = resourceModel.getResourceType();
		if (resourceType == null) {
			return true; // effectively disable "upgrade"
		}
		IContentType contentType =  resourceType.getContentType();
		return ObjectTools.equals(contentType, XmlEntityMappings.CONTENT_TYPE);
	}

	private JpaProject getJpaProject(IProject project) {
		return (JpaProject) project.getAdapter(JpaProject.class);
	}
}
