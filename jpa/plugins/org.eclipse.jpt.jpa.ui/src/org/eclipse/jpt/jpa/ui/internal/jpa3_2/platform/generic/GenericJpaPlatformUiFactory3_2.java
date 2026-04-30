/*******************************************************************************
 * Copyright (c) 2026 Lakshminarayana Nekkanti. All rights reserved.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Lakshminarayana Nekkanti - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa3_2.platform.generic;

import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.JpaPlatformUiFactory;
import org.eclipse.jpt.jpa.ui.internal.jpa3_2.GenericJpaPlatformUiProvider3_2;
import org.eclipse.jpt.jpa.ui.internal.platform.generic.GenericJpaPlatformUi;
import org.eclipse.jpt.jpa.ui.internal.platform.generic.GenericJpaPlatformUiFactory;

public class GenericJpaPlatformUiFactory3_2 implements JpaPlatformUiFactory {

	public JpaPlatformUi buildJpaPlatformUi() {
		return new GenericJpaPlatformUi(GenericJpaPlatformUiFactory.NAVIGATOR_FACTORY_PROVIDER,
				GenericJpaPlatformUiProvider3_2.instance());
	}
}
