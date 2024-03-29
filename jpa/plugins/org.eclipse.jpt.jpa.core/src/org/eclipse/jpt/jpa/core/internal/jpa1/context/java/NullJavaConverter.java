/*******************************************************************************
 * Copyright (c) 2010, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaConverter;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;

public class NullJavaConverter
	extends AbstractJavaContextModel<JavaAttributeMapping>
	implements JavaConverter
{
	public NullJavaConverter(JavaAttributeMapping parent) {
		super(parent);
	}

	public Class<? extends Converter> getConverterType() {
		return null;
	}

	public Annotation getConverterAnnotation() {
		return null;
	}

	public TextRange getValidationTextRange() {
		return this.parent.getValidationTextRange();
	}
}
