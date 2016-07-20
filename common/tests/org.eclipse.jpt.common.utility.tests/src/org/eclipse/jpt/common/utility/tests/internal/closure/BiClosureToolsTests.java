/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.closure;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.eclipse.jpt.common.utility.closure.BiClosure;
import org.eclipse.jpt.common.utility.closure.InterruptibleBiClosure;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.InterruptibleCommand;
import org.eclipse.jpt.common.utility.factory.Factory;
import org.eclipse.jpt.common.utility.factory.InterruptibleFactory;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.closure.BiClosureAdapter;
import org.eclipse.jpt.common.utility.internal.closure.BiClosureTools;
import org.eclipse.jpt.common.utility.internal.closure.BiClosureWrapper;
import org.eclipse.jpt.common.utility.internal.closure.InterruptibleBiClosureAdapter;
import org.eclipse.jpt.common.utility.internal.closure.InterruptibleBiClosureWrapper;
import org.eclipse.jpt.common.utility.internal.closure.ThreadLocalBiClosure;
import org.eclipse.jpt.common.utility.internal.closure.ThreadLocalInterruptibleBiClosure;
import org.eclipse.jpt.common.utility.internal.exception.CollectingExceptionHandler;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.reference.SimpleIntReference;
import org.eclipse.jpt.common.utility.reference.ModifiableIntReference;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import org.eclipse.jpt.common.utility.tests.internal.closure.ClosureToolsTests.LocalCommand;
import org.eclipse.jpt.common.utility.tests.internal.closure.ClosureToolsTests.LocalFactory;
import org.eclipse.jpt.common.utility.tests.internal.closure.ClosureToolsTests.LocalInterruptibleCommand;
import org.eclipse.jpt.common.utility.tests.internal.closure.ClosureToolsTests.LocalInterruptibleFactory;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class BiClosureToolsTests
	extends TestCase
{
	public BiClosureToolsTests(String name) {
		super(name);
	}

	public void testAdaptCommand_execute() {
		LocalCommand command = new LocalCommand();
		BiClosure<Boolean, Boolean> closure = BiClosureTools.adapt(command);
		closure.execute(Boolean.FALSE, Boolean.TRUE);
		assertEquals(1, command.count);
	}

	public void testAdaptCommand_toString() {
		LocalCommand command = new LocalCommand();
		BiClosure<Boolean, Boolean> closure = BiClosureTools.adapt(command);
		assertTrue(closure.toString().indexOf("CommandBiClosure") != -1);
		assertTrue(closure.toString().indexOf("LocalCommand") != -1);
	}

	public void testAdaptCommand_NPE() {
		boolean exCaught = false;
		try {
			BiClosure<Boolean, Boolean> closure = BiClosureTools.adapt((Command) null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAdaptInterruptibleCommand_execute() throws Exception {
		LocalInterruptibleCommand command = new LocalInterruptibleCommand();
		InterruptibleBiClosure<Boolean, Boolean> closure = BiClosureTools.adapt(command);
		closure.execute(Boolean.FALSE, Boolean.TRUE);
		assertEquals(1, command.count);
	}

	public void testAdaptInterruptibleCommand_toString() throws Exception {
		LocalInterruptibleCommand command = new LocalInterruptibleCommand();
		InterruptibleBiClosure<Boolean, Boolean> closure = BiClosureTools.adapt(command);
		assertTrue(closure.toString().indexOf("InterruptibleCommandBiClosure") != -1);
		assertTrue(closure.toString().indexOf("LocalInterruptibleCommand") != -1);
	}

	public void testAdaptInterruptibleCommand_NPE() {
		boolean exCaught = false;
		try {
			InterruptibleBiClosure<Boolean, Boolean> closure = BiClosureTools.adapt((InterruptibleCommand) null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAdaptFactory_execute() throws Exception {
		LocalFactory<Boolean> factory = new LocalFactory<>();
		BiClosure<Boolean, Boolean> closure = BiClosureTools.adapt(factory);
		closure.execute(Boolean.FALSE, Boolean.TRUE);
		assertEquals(1, factory.count);
	}

	public void testAdaptFactory_toString() throws Exception {
		LocalFactory<Boolean> factory = new LocalFactory<>();
		BiClosure<Boolean, Boolean> closure = BiClosureTools.adapt(factory);
		assertTrue(closure.toString().indexOf("FactoryBiClosure") != -1);
		assertTrue(closure.toString().indexOf("LocalFactory") != -1);
	}

	public void testAdaptFactory_NPE() {
		boolean exCaught = false;
		try {
			BiClosure<Boolean, Boolean> closure = BiClosureTools.adapt((Factory<Boolean>) null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAdaptInterruptibleFactory_execute() throws Exception {
		LocalInterruptibleFactory<Boolean> factory = new LocalInterruptibleFactory<>();
		InterruptibleBiClosure<Boolean, Boolean> closure = BiClosureTools.adapt(factory);
		closure.execute(Boolean.FALSE, Boolean.TRUE);
		assertEquals(1, factory.count);
	}

	public void testAdaptInterruptibleFactory_toString() throws Exception {
		LocalInterruptibleFactory<Boolean> factory = new LocalInterruptibleFactory<>();
		InterruptibleBiClosure<Boolean, Boolean> closure = BiClosureTools.adapt(factory);
		assertTrue(closure.toString().indexOf("InterruptibleFactoryBiClosure") != -1);
		assertTrue(closure.toString().indexOf("LocalInterruptibleFactory") != -1);
	}

	public void testAdaptInterruptibleFactory_NPE() {
		boolean exCaught = false;
		try {
			InterruptibleBiClosure<Boolean, Boolean> closure = BiClosureTools.adapt((InterruptibleFactory<Boolean>) null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testNullBiClosure_execute() throws Exception {
		BiClosure<Boolean, Object> closure = BiClosureTools.nullBiClosure();
		closure.execute(Boolean.FALSE, null);
		assertNotNull(closure);
	}

	public void testNullBiClosure_toString() throws Exception {
		BiClosure<Boolean, Object> closure = BiClosureTools.nullBiClosure();
		assertTrue(closure.toString().indexOf("NullBiClosure") != -1);
	}

	public void testNullBiClosure_serialization() throws Exception {
		BiClosure<Boolean, Object> closure = BiClosureTools.nullBiClosure();
		Object clone = TestTools.serialize(closure);
		assertSame(closure, clone);
	}

	public void testThreadLocalBiClosure_execute() throws Exception {
		final ModifiableIntReference ref = new SimpleIntReference();
		final ThreadLocalBiClosure<Integer, Integer> closure = BiClosureTools.threadLocalBiClosure();
		closure.execute(Integer.valueOf(3), Integer.valueOf(2));
		assertEquals(0, ref.getValue());
		Runnable r = new Runnable() {
			@Override
			public void run() {
				closure.set(new BiClosureAdapter<Integer, Integer>() {
					@Override
					public void execute(Integer integer1, Integer integer2) {
						ref.add(integer1.intValue());
						ref.add(integer2.intValue());
					}
				});
				closure.execute(Integer.valueOf(3), Integer.valueOf(2));
			}
		};
		Thread thread = new Thread(r);
		thread.start();
		thread.join();
		assertEquals(5, ref.getValue());
	}

	public void testThreadLocalBiClosure_toString() {
		ThreadLocalBiClosure<Boolean, Boolean> closure = BiClosureTools.threadLocalBiClosure();
		assertTrue(closure.toString().indexOf("NullBiClosure") != -1);
	}

	public void testThreadLocalBiClosure_NPE() {
		boolean exCaught = false;
		try {
			ThreadLocalBiClosure<Boolean, Boolean> closure = BiClosureTools.threadLocalBiClosure(null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testThreadLocalInterruptibleBiClosure_execute() throws Exception {
		final ModifiableIntReference ref = new SimpleIntReference();
		final ThreadLocalInterruptibleBiClosure<Integer, Integer> closure = BiClosureTools.threadLocalInterruptibleBiClosure();
		closure.execute(Integer.valueOf(3), Integer.valueOf(2));
		assertEquals(0, ref.getValue());
		Runnable r = new Runnable() {
			@Override
			public void run() {
				closure.set(new BiClosureAdapter<Integer, Integer>() {
					@Override
					public void execute(Integer integer1, Integer integer2) {
						ref.add(integer1.intValue());
						ref.add(integer2.intValue());
					}
				});
				try {
					closure.execute(Integer.valueOf(3), Integer.valueOf(2));
				} catch (InterruptedException ex) {
					throw new RuntimeException(ex);
				}
			}
		};
		Thread thread = new Thread(r);
		thread.start();
		thread.join();
		assertEquals(5, ref.getValue());
	}

	public void testThreadLocalInterruptibleBiClosure_toString() {
		ThreadLocalInterruptibleBiClosure<Boolean, Boolean> closure = BiClosureTools.threadLocalInterruptibleBiClosure();
		assertTrue(closure.toString().indexOf("NullBiClosure") != -1);
	}

	public void testThreadLocalInterruptibleBiClosure_NPE() {
		boolean exCaught = false;
		try {
			ThreadLocalInterruptibleBiClosure<Boolean, Boolean> closure = BiClosureTools.threadLocalInterruptibleBiClosure(null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testWrap_execute() throws Exception {
		final ModifiableIntReference ref = new SimpleIntReference();
		BiClosureWrapper<Integer, Integer> closure = BiClosureTools.wrap(new BiClosureAdapter<Integer, Integer>() {
			@Override
			public void execute(Integer argument1, Integer argument2) {
				ref.add(argument1.intValue());
				ref.add(argument2.intValue());
			}
		});
		closure.execute(Integer.valueOf(3), Integer.valueOf(2));
		assertEquals(5, ref.getValue());
		closure.setClosure(new BiClosureAdapter<Integer, Integer>() {
			@Override
			public void execute(Integer argument1, Integer argument2) {
				ref.add(2 * argument1.intValue());
				ref.add(2 * argument2.intValue());
			}
		});
		closure.execute(Integer.valueOf(3), Integer.valueOf(2));
		assertEquals(15, ref.getValue());
	}

	public void testWrap_toString() {
		BiClosureWrapper<Integer, Integer> closure = BiClosureTools.wrap(new BiClosureAdapter<Integer, Integer>() {
			@Override
			public void execute(Integer argument1, Integer argument2) {
				// NOP
			}
		});
		assertTrue(closure.toString().indexOf("BiClosureWrapper") != -1);
	}

	public void testWrap_NPE1() {
		boolean exCaught = false;
		try {
			BiClosure<Integer, Integer> closure = BiClosureTools.wrap((BiClosure<Integer, Integer>) null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testWrap_NPE2() {
		BiClosureWrapper<Integer, Integer> closure = BiClosureTools.wrap(new BiClosureAdapter<Integer, Integer>());
		boolean exCaught = false;
		try {
			closure.setClosure(null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testWrapInterruptible_execute() throws Exception {
		final ModifiableIntReference ref = new SimpleIntReference();
		InterruptibleBiClosureWrapper<Integer, Integer> closure = BiClosureTools.wrap(new InterruptibleBiClosureAdapter<Integer, Integer>() {
			@Override
			public void execute(Integer argument1, Integer argument2) throws InterruptedException {
				ref.add(argument1.intValue());
				ref.add(argument2.intValue());
			}
		});
		closure.execute(Integer.valueOf(3), Integer.valueOf(2));
		assertEquals(5, ref.getValue());
		closure.setClosure(new InterruptibleBiClosureAdapter<Integer, Integer>() {
			@Override
			public void execute(Integer argument1, Integer argument2) throws InterruptedException {
				ref.add(2 * argument1.intValue());
				ref.add(2 * argument2.intValue());
			}
		});
		closure.execute(Integer.valueOf(3), Integer.valueOf(2));
		assertEquals(15, ref.getValue());
	}

	public void testWrapInterruptible_toString() {
		InterruptibleBiClosureWrapper<Integer, Integer> closure = BiClosureTools.wrap(new InterruptibleBiClosureAdapter<Integer, Integer>() {
			@Override
			public void execute(Integer argument1, Integer argument2) throws InterruptedException {
				// NOP
			}
		});
		assertTrue(closure.toString().indexOf("InterruptibleBiClosureWrapper") != -1);
	}

	public void testWrapInterruptible_NPE1() {
		boolean exCaught = false;
		try {
			InterruptibleBiClosure<Integer, Integer> closure = BiClosureTools.wrap((InterruptibleBiClosure<Integer, Integer>) null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testWrapInterruptible_NPE2() {
		InterruptibleBiClosureWrapper<Integer, Integer> closure = BiClosureTools.wrap(new InterruptibleBiClosureAdapter<Integer, Integer>());
		boolean exCaught = false;
		try {
			closure.setClosure(null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testSafeClosure_execute() throws Exception {
		LocalBiClosure closure1 = new LocalBiClosure();
		BiClosure<Integer, Integer> closure2 = BiClosureTools.safeBiClosure(closure1);
		closure2.execute(Integer.valueOf(3), Integer.valueOf(2));
		assertEquals(5, closure1.count);
		CollectingExceptionHandler handler = new CollectingExceptionHandler();
		closure2 = BiClosureTools.safeBiClosure(closure1, handler);
		closure2.execute(null, null);
		assertEquals(1, IterableTools.size(handler.getExceptions()));
	}

	public void testSafeBiClosure_toString() {
		BiClosure<Integer, Integer> closure = BiClosureTools.safeBiClosure(new LocalBiClosure());
		assertTrue(closure.toString().indexOf("SafeBiClosureWrapper") != -1);
		assertTrue(closure.toString().indexOf("LocalBiClosure") != -1);
	}

	public void testSafeBiClosure_NPE1() {
		boolean exCaught = false;
		try {
			BiClosure<Integer, Integer> closure = BiClosureTools.safeBiClosure((BiClosure<Integer, Integer>) null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testSafeBiClosure_NPE2() {
		boolean exCaught = false;
		try {
			BiClosure<Integer, Integer> closure = BiClosureTools.safeBiClosure(new LocalBiClosure(), null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testSafeInterruptibleClosure_execute() throws Exception {
		LocalInterruptibleBiClosure closure1 = new LocalInterruptibleBiClosure();
		InterruptibleBiClosure<Integer, Integer> closure2 = BiClosureTools.safeBiClosure(closure1);
		closure2.execute(Integer.valueOf(3), Integer.valueOf(2));
		assertEquals(5, closure1.count);
		CollectingExceptionHandler handler = new CollectingExceptionHandler();
		closure2 = BiClosureTools.safeBiClosure(closure1, handler);
		closure2.execute(null, null);
		assertEquals(1, IterableTools.size(handler.getExceptions()));
	}

	public void testSafeInterruptibleBiClosure_toString() {
		InterruptibleBiClosure<Integer, Integer> closure = BiClosureTools.safeBiClosure(new LocalInterruptibleBiClosure());
		assertTrue(closure.toString().indexOf("SafeInterruptibleBiClosureWrapper") != -1);
		assertTrue(closure.toString().indexOf("LocalInterruptibleBiClosure") != -1);
	}

	public void testSafeInterruptibleBiClosure_NPE1() {
		boolean exCaught = false;
		try {
			InterruptibleBiClosure<Integer, Integer> closure = BiClosureTools.safeBiClosure((InterruptibleBiClosure<Integer, Integer>) null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testSafeInterruptibleBiClosure_NPE2() {
		boolean exCaught = false;
		try {
			InterruptibleBiClosure<Integer, Integer> closure = BiClosureTools.safeBiClosure(new LocalInterruptibleBiClosure(), null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testCompositeClosure_execute1() throws Exception {
		LocalBiClosure closure1 = new LocalBiClosure();
		LocalBiClosure closure2 = new LocalBiClosure();
		BiClosure<Integer, Integer> closure = BiClosureTools.compositeBiClosure(closure1, closure2);
		closure.execute(Integer.valueOf(3), Integer.valueOf(2));
		assertEquals(5, closure1.count);
		assertEquals(5, closure2.count);
		@SuppressWarnings("unchecked")
		BiClosure<Integer, Integer>[] closureArray = new BiClosure[0];
		closure = BiClosureTools.compositeBiClosure(closureArray);
		closure.execute(Integer.valueOf(3), Integer.valueOf(2));
	}

	public void testCompositeBiClosure_execute2() throws Exception {
		LocalBiClosure closure1 = new LocalBiClosure();
		LocalBiClosure closure2 = new LocalBiClosure();
		ArrayList<LocalBiClosure> list = new ArrayList<>();
		list.add(closure1);
		list.add(closure2);
		BiClosure<Integer, Integer> closure = BiClosureTools.compositeBiClosure(list);
		closure.execute(Integer.valueOf(3), Integer.valueOf(2));
		assertEquals(5, closure1.count);
		assertEquals(5, closure2.count);
		list.clear();
		closure = BiClosureTools.compositeBiClosure(list);
		closure.execute(Integer.valueOf(3), Integer.valueOf(2));
	}

	public void testCompositeBiClosure_toString() {
		LocalBiClosure closure1 = new LocalBiClosure();
		LocalBiClosure closure2 = new LocalBiClosure();
		BiClosure<Integer, Integer> closure = BiClosureTools.compositeBiClosure(closure1, closure2);
		assertTrue(closure.toString().indexOf("CompositeBiClosure") != -1);
		assertTrue(closure.toString().indexOf("LocalBiClosure") != -1);
	}

	public void testCompositeBiClosure_NPE1() {
		LocalBiClosure closure1 = new LocalBiClosure();
		boolean exCaught = false;
		try {
			BiClosure<Integer, Integer> closure = BiClosureTools.compositeBiClosure(closure1, null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testCompositeBiClosure_NPE2() {
		boolean exCaught = false;
		try {
			BiClosure<Integer, Integer> closure = BiClosureTools.compositeBiClosure((Iterable<BiClosure<Integer, Integer>>) null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testCompositeInterruptibleClosure_execute1() throws Exception {
		LocalInterruptibleBiClosure closure1 = new LocalInterruptibleBiClosure();
		LocalInterruptibleBiClosure closure2 = new LocalInterruptibleBiClosure();
		InterruptibleBiClosure<Integer, Integer> closure = BiClosureTools.compositeInterruptibleBiClosure(closure1, closure2);
		closure.execute(Integer.valueOf(3), Integer.valueOf(2));
		assertEquals(5, closure1.count);
		assertEquals(5, closure2.count);
		@SuppressWarnings("unchecked")
		InterruptibleBiClosure<Integer, Integer>[] closureArray = new InterruptibleBiClosure[0];
		closure = BiClosureTools.compositeInterruptibleBiClosure(closureArray);
		closure.execute(Integer.valueOf(3), Integer.valueOf(2));
	}

	public void testCompositeInterruptibleBiClosure_execute2() throws Exception {
		LocalInterruptibleBiClosure closure1 = new LocalInterruptibleBiClosure();
		LocalInterruptibleBiClosure closure2 = new LocalInterruptibleBiClosure();
		ArrayList<LocalInterruptibleBiClosure> list = new ArrayList<>();
		list.add(closure1);
		list.add(closure2);
		InterruptibleBiClosure<Integer, Integer> closure = BiClosureTools.compositeInterruptibleBiClosure(list);
		closure.execute(Integer.valueOf(3), Integer.valueOf(2));
		assertEquals(5, closure1.count);
		assertEquals(5, closure2.count);
		list.clear();
		closure = BiClosureTools.compositeInterruptibleBiClosure(list);
		closure.execute(Integer.valueOf(3), Integer.valueOf(2));
	}

	public void testCompositeInterruptibleBiClosure_toString() {
		LocalInterruptibleBiClosure closure1 = new LocalInterruptibleBiClosure();
		LocalInterruptibleBiClosure closure2 = new LocalInterruptibleBiClosure();
		InterruptibleBiClosure<Integer, Integer> closure = BiClosureTools.compositeInterruptibleBiClosure(closure1, closure2);
		assertTrue(closure.toString().indexOf("CompositeInterruptibleBiClosure") != -1);
		assertTrue(closure.toString().indexOf("LocalInterruptibleBiClosure") != -1);
	}

	public void testCompositeInterruptibleBiClosure_NPE1() {
		LocalInterruptibleBiClosure closure1 = new LocalInterruptibleBiClosure();
		boolean exCaught = false;
		try {
			InterruptibleBiClosure<Integer, Integer> closure = BiClosureTools.compositeInterruptibleBiClosure(closure1, null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testCompositeInterruptibleBiClosure_NPE2() {
		boolean exCaught = false;
		try {
			InterruptibleBiClosure<Integer, Integer> closure = BiClosureTools.compositeInterruptibleBiClosure((Iterable<InterruptibleBiClosure<Integer, Integer>>) null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testDisabledBiClosure_execute() {
		BiClosure<Integer, Integer> closure = BiClosureTools.disabledBiClosure();
		boolean exCaught = false;
		try {
			closure.execute(null, null);
			fail();
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testDisabledBiClosure_toString() {
		BiClosure<Integer, Integer> closure = BiClosureTools.disabledBiClosure();
		assertTrue(closure.toString().indexOf("DisabledBiClosure") != -1);
	}

	public void testDisabledBiClosure_serialization() throws Exception {
		BiClosure<Integer, Integer> closure = BiClosureTools.disabledBiClosure();
		assertSame(closure, TestTools.serialize(closure));
	}

	public void testInterruptedBiClosure_execute() {
		InterruptibleBiClosure<Integer, Integer> closure = BiClosureTools.interruptedBiClosure();
		boolean exCaught = false;
		try {
			closure.execute(null, null);
			fail();
		} catch (InterruptedException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testInterruptedBiClosure_toString() {
		InterruptibleBiClosure<Integer, Integer> closure = BiClosureTools.interruptedBiClosure();
		assertTrue(closure.toString().indexOf("InterruptedBiClosure") != -1);
	}

	public void testInterruptedBiClosure_serialization() throws Exception {
		InterruptibleBiClosure<Integer, Integer> closure = BiClosureTools.interruptedBiClosure();
		assertSame(closure, TestTools.serialize(closure));
	}

	public void testRepeatingBiClosure_execute1() throws Exception {
		LocalBiClosure closure1 = new LocalBiClosure();
		BiClosure<Integer, Integer> closure2 = BiClosureTools.repeatingBiClosure(closure1, 3);
		closure2.execute(Integer.valueOf(3), Integer.valueOf(2));
		assertEquals(15, closure1.count);
	}

	public void testRepeatingBiClosure_execute2() throws Exception {
		LocalBiClosure closure1 = new LocalBiClosure();
		BiClosure<Integer, Integer> closure2 = BiClosureTools.repeatingBiClosure(closure1, 0);
		closure2.execute(Integer.valueOf(3), Integer.valueOf(2));
		assertEquals(0, closure1.count);
	}

	public void testRepeatingBiClosure_toString() {
		LocalBiClosure closure1 = new LocalBiClosure();
		BiClosure<Integer, Integer> closure2 = BiClosureTools.repeatingBiClosure(closure1, 3);
		assertTrue(closure2.toString().indexOf("RepeatingBiClosure") != -1);
		assertTrue(closure2.toString().indexOf("LocalBiClosure") != -1);
	}

	public void testRepeatingBiClosure_NPE1() {
		boolean exCaught = false;
		try {
			BiClosure<Integer, Integer> closure = BiClosureTools.repeatingBiClosure((BiClosure<Integer, Integer>) null, 3);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRepeatingBiClosure_NPE2() {
		LocalBiClosure closure1 = new LocalBiClosure();
		boolean exCaught = false;
		try {
			BiClosure<Integer, Integer> closure2 = BiClosureTools.repeatingBiClosure(closure1, -3);
			fail("bogus: " + closure2);
		} catch (IndexOutOfBoundsException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRepeatingInterruptibleBiClosure_execute1() throws Exception {
		LocalInterruptibleBiClosure closure1 = new LocalInterruptibleBiClosure();
		InterruptibleBiClosure<Integer, Integer> closure2 = BiClosureTools.repeatingBiClosure(closure1, 3);
		closure2.execute(Integer.valueOf(3), Integer.valueOf(2));
		assertEquals(15, closure1.count);
	}

	public void testRepeatingInterruptibleBiClosure_execute2() throws Exception {
		LocalInterruptibleBiClosure closure1 = new LocalInterruptibleBiClosure();
		InterruptibleBiClosure<Integer, Integer> closure2 = BiClosureTools.repeatingBiClosure(closure1, 0);
		closure2.execute(Integer.valueOf(3), Integer.valueOf(2));
		assertEquals(0, closure1.count);
	}

	public void testRepeatingInterruptibleBiClosure_toString() {
		LocalInterruptibleBiClosure closure1 = new LocalInterruptibleBiClosure();
		InterruptibleBiClosure<Integer, Integer> closure2 = BiClosureTools.repeatingBiClosure(closure1, 3);
		assertTrue(closure2.toString().indexOf("RepeatingInterruptibleBiClosure") != -1);
		assertTrue(closure2.toString().indexOf("LocalInterruptibleBiClosure") != -1);
	}

	public void testRepeatingInterruptibleBiClosure_NPE1() {
		boolean exCaught = false;
		try {
			InterruptibleBiClosure<Integer, Integer> closure = BiClosureTools.repeatingBiClosure((InterruptibleBiClosure<Integer, Integer>) null, 3);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRepeatingInterruptibleBiClosure_NPE2() {
		LocalInterruptibleBiClosure closure1 = new LocalInterruptibleBiClosure();
		boolean exCaught = false;
		try {
			InterruptibleBiClosure<Integer, Integer> closure2 = BiClosureTools.repeatingBiClosure(closure1, -3);
			fail("bogus: " + closure2);
		} catch (IndexOutOfBoundsException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testConstructor() {
		boolean exCaught = false;
		try {
			Object at = ClassTools.newInstance(BiClosureTools.class);
			fail("bogus: " + at);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof InvocationTargetException) {
				if (ex.getCause().getCause() instanceof UnsupportedOperationException) {
					exCaught = true;
				}
			}
		}
		assertTrue(exCaught);
	}

	// ********** test classes **********

	public static class LocalBiClosure
		extends BiClosureAdapter<Integer, Integer>
	{
		public int count = 0;

		@Override
		public void execute(Integer argument1, Integer argument2) {
			this.count += argument1.intValue(); // possible NPE
			this.count += argument2.intValue(); // possible NPE
		}
	}

	public static class LocalInterruptibleBiClosure
		extends InterruptibleBiClosureAdapter<Integer, Integer>
	{
		public int count = 0;

		@Override
		public void execute(Integer argument1, Integer argument2) throws InterruptedException {
			this.count += argument1.intValue(); // possible NPE
			this.count += argument2.intValue(); // possible NPE
		}
	}
}
