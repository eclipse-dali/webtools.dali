/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

/**
 * A <code>NotBooleanTransformer</code> will transform a
 * {@link Boolean} to its NOT value:<ul>
 * <li>If the original {@link Boolean} is {@link Boolean#TRUE},
 * the transformer will return {@link Boolean#FALSE}.
 * <li>If the original {@link Boolean} is {@link Boolean#FALSE},
 * the transformer will return {@link Boolean#TRUE}.
 * <li>If the original {@link Boolean} is <code>null</code>,
 * the transformer will return <code>null</code>.
 * </ul>
 */
public class NotBooleanTransformer
	implements BidiTransformer<Boolean, Boolean>
{
	public static final BidiTransformer<Boolean, Boolean> INSTANCE = new NotBooleanTransformer();

	public static BidiTransformer<Boolean, Boolean> instance() {
		return INSTANCE;
	}

	// ensure single instance
	private NotBooleanTransformer() {
		super();
	}

	public Boolean transform(Boolean b) {
		return (b == null) ? null : BooleanTools.not(b);
	}

	public Boolean reverseTransform(Boolean b) {
		return (b == null) ? null : BooleanTools.not(b);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	private static final long serialVersionUID = 1L;
	private Object readResolve() {
		// replace this object with the singleton
		return INSTANCE;
	}

}
