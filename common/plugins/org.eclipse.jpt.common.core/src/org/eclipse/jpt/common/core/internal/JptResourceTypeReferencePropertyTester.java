/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal;

import java.util.regex.Pattern;
import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.JptResourceTypeReference;
import org.eclipse.jpt.common.core.internal.utility.ContentTypeTools;

/**
 * Property tester for {@link JptResourceTypeReference}.
 * See <code>org.eclipse.jpt.common.core/plugin.xml:org.eclipse.core.expressions.propertyTesters</code>
 * @see JptResourceType#isKindOf(JptResourceType)
 */
public class JptResourceTypeReferencePropertyTester
	extends PropertyTester
{
	/**
	 * To test a {@link JptResourceTypeReference resource type reference}, specify
	 * the resource type identifier and, optionally, a version; using the format
	 * <code>"<em>&lt;resource-type-id&gt;</em>:<em>&lt;version&gt;</em>"</code>
	 * (e.g. <code>"org.eclipse.jpt.jpa.eclipselink.core.content.orm:2.1"</code>)
	 */
	public static final String RESOURCE_TYPE_IS_KIND_OF = "resourceTypeIsKindOf"; //$NON-NLS-1$
	public static final Pattern RESOURCE_TYPE_PATTERN = Pattern.compile(":"); //$NON-NLS-1$


	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (receiver instanceof JptResourceTypeReference) {
			return this.test((JptResourceTypeReference) receiver, property, expectedValue);
		}
		return false;
	}

	private boolean test(JptResourceTypeReference ref, String property, Object expectedValue) {
		if (property.equals(RESOURCE_TYPE_IS_KIND_OF)) {
			return this.resourceTypeIsKindOf(ref, (String) expectedValue);
		}
		return false;
	}

	private boolean resourceTypeIsKindOf(JptResourceTypeReference ref, String expectedValue) {
		return this.resourceTypeIsKindOf(ref, RESOURCE_TYPE_PATTERN.split(expectedValue));
	}

	private boolean resourceTypeIsKindOf(JptResourceTypeReference ref, String[] subStrings) {
		int len = subStrings.length;
		if ((len < 1) || (len > 2)) {
			return false;
		}
		String contentTypeID = subStrings[0];
		String version = (len == 2) ? subStrings[1] : JptResourceType.UNDETERMINED_VERSION;
		return this.resourceTypeIsKindOf(ref, contentTypeID, version);
	}

	private boolean resourceTypeIsKindOf(JptResourceTypeReference ref, String contentTypeID, String version) {
		IContentType contentType = this.getContentType(contentTypeID);
		return (contentType != null) && this.resourceTypeIsKindOf(ref, contentType, version);
	}

	private IContentType getContentType(String contentTypeID) {
		return Platform.getContentTypeManager().getContentType(contentTypeID);
	}

	private boolean resourceTypeIsKindOf(JptResourceTypeReference ref, IContentType contentType, String version) {
		JptResourceType resourceType = ContentTypeTools.getResourceType(contentType, version);
		return (resourceType != null) && ref.getResourceType().isKindOf(resourceType);
	}
}
