/*******************************************************************************
* Copyright (c) 2009, 2013 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License 2.0, which accompanies this distribution
* and is available at https://www.eclipse.org/legal/epl-2.0/.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.jpa.core.internal.resource.java.binary.BinaryNamedQueryAnnotation;

/**
 * <code>javax.persistence.NamedQuery</code>
 */
public final class BinaryNamedQueryAnnotation1_0
	extends BinaryNamedQueryAnnotation
{
	public BinaryNamedQueryAnnotation1_0(JavaResourceModel parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
	}
}
