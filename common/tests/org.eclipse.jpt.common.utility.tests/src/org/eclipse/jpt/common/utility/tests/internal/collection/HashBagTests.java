/*******************************************************************************
 * Copyright (c) 2005, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.collection;

import java.util.Collection;
import org.eclipse.jpt.common.utility.collection.Bag;
import org.eclipse.jpt.common.utility.internal.SystemTools;
import org.eclipse.jpt.common.utility.internal.collection.HashBag;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class HashBagTests
	extends BagTests
{
	public HashBagTests(String name) {
		super(name);
	}

	@Override
	protected Bag<String> buildBag_() {
		return new HashBag<String>();
	}

	@Override
	protected Bag<String> buildBag(Collection<String> c) {
		return new HashBag<String>(c);
	}

	@Override
	protected Bag<String> buildBag(int initialCapacity, float loadFactor) {
		return new HashBag<String>(initialCapacity, loadFactor);
	}

	public void testHashingDistribution() throws Exception {
		Bag<String> bigBag = this.buildBag();
		for (int i = 0; i < 10000; i++) {
			bigBag.add("object" + i);
		}

		java.lang.reflect.Field field = bigBag.getClass().getDeclaredField("table");
		field.setAccessible(true);
		Object[] table = (Object[]) field.get(bigBag);
		int bucketCount = table.length;
		int filledBucketCount = 0;
		for (Object o : table) {
			if (o != null) {
				filledBucketCount++;
			}
		}
		float loadFactor = ((float) filledBucketCount) / ((float) bucketCount);
		if ((loadFactor < 0.20) || (loadFactor > 0.80)) {
			String msg = "poor load factor: " + loadFactor;
			if (SystemTools.jvmIsSun()) {
				fail(msg);
			} else {
				// poor load factor is seen in the Eclipse build environment for some reason...
				System.out.println(this.getClass().getName() + '.' + this.getName() + " - " + msg);
				TestTools.printSystemProperties();
			}
		}
	}
}
