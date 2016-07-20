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
import java.util.List;
import org.eclipse.jpt.common.utility.Association;
import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.closure.InterruptibleClosure;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.InterruptibleCommand;
import org.eclipse.jpt.common.utility.factory.Factory;
import org.eclipse.jpt.common.utility.factory.InterruptibleFactory;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.SimpleAssociation;
import org.eclipse.jpt.common.utility.internal.closure.ClosureAdapter;
import org.eclipse.jpt.common.utility.internal.closure.ClosureTools;
import org.eclipse.jpt.common.utility.internal.closure.ClosureWrapper;
import org.eclipse.jpt.common.utility.internal.closure.InterruptibleClosureAdapter;
import org.eclipse.jpt.common.utility.internal.closure.InterruptibleClosureWrapper;
import org.eclipse.jpt.common.utility.internal.closure.ThreadLocalClosure;
import org.eclipse.jpt.common.utility.internal.closure.ThreadLocalInterruptibleClosure;
import org.eclipse.jpt.common.utility.internal.command.CommandAdapter;
import org.eclipse.jpt.common.utility.internal.command.CommandTools;
import org.eclipse.jpt.common.utility.internal.command.InterruptibleCommandAdapter;
import org.eclipse.jpt.common.utility.internal.exception.CollectingExceptionHandler;
import org.eclipse.jpt.common.utility.internal.factory.FactoryAdapter;
import org.eclipse.jpt.common.utility.internal.factory.InterruptibleFactoryAdapter;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.internal.reference.SimpleIntReference;
import org.eclipse.jpt.common.utility.internal.transformer.InterruptibleTransformerAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.reference.ModifiableIntReference;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import org.eclipse.jpt.common.utility.transformer.InterruptibleTransformer;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class ClosureToolsTests
	extends TestCase
{
	public ClosureToolsTests(String name) {
		super(name);
	}

	public void testAdaptCommand_execute() {
		LocalCommand command = new LocalCommand();
		Closure<Boolean> closure = ClosureTools.adapt(command);
		closure.execute(Boolean.FALSE);
		assertEquals(1, command.count);
	}

	public void testAdaptCommand_toString() {
		LocalCommand command = new LocalCommand();
		Closure<Boolean> closure = ClosureTools.adapt(command);
		assertTrue(closure.toString().indexOf("CommandClosure") != -1);
		assertTrue(closure.toString().indexOf("LocalCommand") != -1);
	}

	public void testAdaptCommand_NPE() {
		boolean exCaught = false;
		try {
			Closure<Boolean> closure = ClosureTools.adapt((Command) null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAdaptInterruptibleCommand_execute() throws Exception {
		LocalInterruptibleCommand command = new LocalInterruptibleCommand();
		InterruptibleClosure<Boolean> closure = ClosureTools.adapt(command);
		closure.execute(Boolean.FALSE);
		assertEquals(1, command.count);
	}

	public void testAdaptInterruptibleCommand_toString() {
		LocalInterruptibleCommand command = new LocalInterruptibleCommand();
		InterruptibleClosure<Boolean> closure = ClosureTools.adapt(command);
		assertTrue(closure.toString().indexOf("InterruptibleCommandClosure") != -1);
		assertTrue(closure.toString().indexOf("LocalInterruptibleCommand") != -1);
	}

	public void testAdaptInterruptibleCommand_NPE() {
		boolean exCaught = false;
		try {
			InterruptibleClosure<Boolean> closure = ClosureTools.adapt((InterruptibleCommand) null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAdaptFactory_execute() {
		LocalFactory<?> factory = new LocalFactory<>();
		Closure<Boolean> closure = ClosureTools.adapt(factory);
		closure.execute(Boolean.FALSE);
		assertEquals(1, factory.count);
	}

	public void testAdaptFactory_toString() {
		LocalFactory<?> factory = new LocalFactory<>();
		Closure<Boolean> closure = ClosureTools.adapt(factory);
		assertTrue(closure.toString().indexOf("FactoryClosure") != -1);
		assertTrue(closure.toString().indexOf("LocalFactory") != -1);
	}

	public void testAdaptFactory_NPE() {
		boolean exCaught = false;
		try {
			Closure<Boolean> closure = ClosureTools.adapt((Factory<?>) null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAdaptInterruptibleFactory_execute() throws Exception {
		LocalInterruptibleFactory<?> factory = new LocalInterruptibleFactory<>();
		InterruptibleClosure<Boolean> closure = ClosureTools.adapt(factory);
		closure.execute(Boolean.FALSE);
		assertEquals(1, factory.count);
	}

	public void testAdaptInterruptibleFactory_toString() {
		LocalInterruptibleFactory<?> factory = new LocalInterruptibleFactory<>();
		InterruptibleClosure<Boolean> closure = ClosureTools.adapt(factory);
		assertTrue(closure.toString().indexOf("InterruptibleFactoryClosure") != -1);
		assertTrue(closure.toString().indexOf("LocalInterruptibleFactory") != -1);
	}

	public void testAdaptInterruptibleFactory_NPE() {
		boolean exCaught = false;
		try {
			InterruptibleClosure<Boolean> closure = ClosureTools.adapt((InterruptibleFactory<?>) null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAdaptTransformer_execute() {
		LocalTransformer<Boolean, ?> transformer = new LocalTransformer<>();
		Closure<Boolean> closure = ClosureTools.adapt(transformer);
		closure.execute(Boolean.FALSE);
		assertEquals(1, transformer.count);
	}

	public void testAdaptTransformer_toString() {
		LocalTransformer<Boolean, ?> transformer = new LocalTransformer<>();
		Closure<Boolean> closure = ClosureTools.adapt(transformer);
		assertTrue(closure.toString().indexOf("TransformerClosure") != -1);
		assertTrue(closure.toString().indexOf("LocalTransformer") != -1);
	}

	public void testAdaptTransformer_NPE() {
		boolean exCaught = false;
		try {
			Closure<Boolean> closure = ClosureTools.adapt((Transformer<Boolean, ?>) null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAdaptInterruptibleTransformer_execute() throws Exception {
		LocalInterruptibleTransformer<Boolean, ?> transformer = new LocalInterruptibleTransformer<>();
		InterruptibleClosure<Boolean> closure = ClosureTools.adapt(transformer);
		closure.execute(Boolean.FALSE);
		assertEquals(1, transformer.count);
	}

	public void testAdaptInterruptibleTransformer_toString() {
		LocalInterruptibleTransformer<Boolean, ?> transformer = new LocalInterruptibleTransformer<>();
		InterruptibleClosure<Boolean> closure = ClosureTools.adapt(transformer);
		assertTrue(closure.toString().indexOf("InterruptibleTransformerClosure") != -1);
		assertTrue(closure.toString().indexOf("LocalInterruptibleTransformer") != -1);
	}

	public void testAdaptInterruptibleTransformer_NPE() {
		boolean exCaught = false;
		try {
			InterruptibleClosure<Boolean> closure = ClosureTools.adapt((InterruptibleTransformer<Boolean, ?>) null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testNullClosure_execute() throws Exception {
		Closure<Boolean> closure = ClosureTools.nullClosure();
		closure.execute(Boolean.FALSE);
		assertNotNull(closure);
	}

	public void testNullClosure_toString() throws Exception {
		Closure<Boolean> closure = ClosureTools.nullClosure();
		assertTrue(closure.toString().indexOf("NullClosure") != -1);
	}

	public void testNullClosure_serialization() throws Exception {
		Closure<Boolean> closure = ClosureTools.nullClosure();
		Object clone = TestTools.serialize(closure);
		assertSame(closure, clone);
	}

	public void testThreadLocalClosure_execute() throws Exception {
		final ModifiableIntReference ref = new SimpleIntReference();
		final ThreadLocalClosure<Integer> closure = ClosureTools.threadLocalClosure();
		closure.execute(Integer.valueOf(3));
		assertEquals(0, ref.getValue());
		Runnable r = new Runnable() {
			@Override
			public void run() {
				closure.set(new ClosureAdapter<Integer>() {
					@Override
					public void execute(Integer integer) {
						ref.add(integer.intValue());
					}
				});
				closure.execute(Integer.valueOf(3));
			}
		};
		Thread thread = new Thread(r);
		thread.start();
		thread.join();
		assertEquals(3, ref.getValue());
	}

	public void testThreadLocalClosure_toString() {
		ThreadLocalClosure<Boolean> closure = ClosureTools.threadLocalClosure();
		assertTrue(closure.toString().indexOf("NullClosure") != -1);
	}

	public void testThreadLocalClosure_NPE() {
		boolean exCaught = false;
		try {
			ThreadLocalClosure<Boolean> closure = ClosureTools.threadLocalClosure(null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testThreadLocalInterruptibleClosure_execute() throws Exception {
		final ModifiableIntReference ref = new SimpleIntReference();
		final ThreadLocalInterruptibleClosure<Integer> closure = ClosureTools.threadLocalInterruptibleClosure();
		closure.execute(Integer.valueOf(3));
		assertEquals(0, ref.getValue());
		Runnable r = new Runnable() {
			@Override
			public void run() {
				closure.set(new ClosureAdapter<Integer>() {
					@Override
					public void execute(Integer integer) {
						ref.add(integer.intValue());
					}
				});
				try {
					closure.execute(Integer.valueOf(3));
				} catch (InterruptedException ex) {
					throw new RuntimeException(ex);
				}
			}
		};
		Thread thread = new Thread(r);
		thread.start();
		thread.join();
		assertEquals(3, ref.getValue());
	}

	public void testThreadLocalInterruptibleClosure_toString() {
		ThreadLocalInterruptibleClosure<Boolean> closure = ClosureTools.threadLocalInterruptibleClosure();
		assertTrue(closure.toString().indexOf("NullClosure") != -1);
	}

	public void testThreadLocalInterruptibleClosure_NPE() {
		boolean exCaught = false;
		try {
			ThreadLocalInterruptibleClosure<Boolean> closure = ClosureTools.threadLocalInterruptibleClosure(null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testNullCheck_execute() throws Exception {
		LocalClosure closure1 = new LocalClosure();
		Closure<Integer> closure2 = ClosureTools.nullCheck(closure1);
		closure2.execute(null);
		assertEquals(0, closure1.count);
		closure2.execute(Integer.valueOf(3));
		assertEquals(3, closure1.count);
	}

	public void testNullCheck_toString() {
		Closure<Integer> closure1 = new LocalClosure();
		Closure<Integer> closure2 = ClosureTools.nullCheck(closure1);
		assertTrue(closure2.toString().indexOf("NullCheckClosure") != -1);
		assertTrue(closure2.toString().indexOf("LocalClosure") != -1);
	}

	public void testNullCheck_NPE1() {
		boolean exCaught = false;
		try {
			Closure<Integer> closure = ClosureTools.nullCheck((Closure<Integer>) null, CommandTools.nullCommand());
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testNullCheck_NPE2() {
		Closure<Integer> closure1 = new LocalClosure();
		boolean exCaught = false;
		try {
			Closure<Integer> closure2 = ClosureTools.nullCheck(closure1, null);
			fail("bogus: " + closure2);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testNullCheckInterruptible_execute() throws Exception {
		LocalInterruptibleClosure closure1 = new LocalInterruptibleClosure();
		InterruptibleClosure<Integer> closure2 = ClosureTools.nullCheck(closure1);
		closure2.execute(null);
		assertEquals(0, closure1.count);
		closure2.execute(Integer.valueOf(3));
		assertEquals(3, closure1.count);
	}

	public void testNullCheckInterruptible_toString() {
		InterruptibleClosure<Integer> closure1 = new LocalInterruptibleClosure();
		InterruptibleClosure<Integer> closure2 = ClosureTools.nullCheck(closure1);
		assertTrue(closure2.toString().indexOf("NullCheckInterruptibleClosure") != -1);
		assertTrue(closure2.toString().indexOf("LocalInterruptibleClosure") != -1);
	}

	public void testNullCheckInterruptible_NPE1() {
		boolean exCaught = false;
		try {
			InterruptibleClosure<Integer> closure = ClosureTools.nullCheck((InterruptibleClosure<Integer>) null, CommandTools.nullCommand());
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testNullCheckInterruptible_NPE2() {
		InterruptibleClosure<Integer> closure1 = new LocalInterruptibleClosure();
		boolean exCaught = false;
		try {
			InterruptibleClosure<Integer> closure2 = ClosureTools.nullCheck(closure1, null);
			fail("bogus: " + closure2);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testWrap_execute() throws Exception {
		final ModifiableIntReference ref = new SimpleIntReference();
		ClosureWrapper<Integer> closure = ClosureTools.wrap(new ClosureAdapter<Integer>() {
			@Override
			public void execute(Integer argument) {
				ref.add(argument.intValue());
			}
		});
		closure.execute(Integer.valueOf(3));
		assertEquals(3, ref.getValue());
		closure.setClosure(new ClosureAdapter<Integer>() {
			@Override
			public void execute(Integer argument) {
				ref.add(2 * argument.intValue());
			}
		});
		closure.execute(Integer.valueOf(3));
		assertEquals(9, ref.getValue());
	}

	public void testWrap_toString() {
		ClosureWrapper<Integer> closure = ClosureTools.wrap(new ClosureAdapter<Integer>() {
			@Override
			public void execute(Integer argument) {
				// NOP
			}
		});
		assertTrue(closure.toString().indexOf("ClosureWrapper") != -1);
	}

	public void testWrap_NPE1() {
		boolean exCaught = false;
		try {
			Closure<Integer> closure = ClosureTools.wrap((Closure<Integer>) null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testWrap_NPE2() {
		ClosureWrapper<Integer> closure = ClosureTools.wrap(new LocalClosure());
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
		InterruptibleClosureWrapper<Integer> closure = ClosureTools.wrap(new InterruptibleClosureAdapter<Integer>() {
			@Override
			public void execute(Integer argument) throws InterruptedException {
				ref.add(argument.intValue());
			}
		});
		closure.execute(Integer.valueOf(3));
		assertEquals(3, ref.getValue());
		closure.setClosure(new InterruptibleClosureAdapter<Integer>() {
			@Override
			public void execute(Integer argument) throws InterruptedException {
				ref.add(2 * argument.intValue());
			}
		});
		closure.execute(Integer.valueOf(3));
		assertEquals(9, ref.getValue());
	}

	public void testWrapInterruptible_toString() {
		InterruptibleClosureWrapper<Integer> closure = ClosureTools.wrap(new InterruptibleClosureAdapter<Integer>() {
			@Override
			public void execute(Integer argument) throws InterruptedException {
				// NOP
			}
		});
		assertTrue(closure.toString().indexOf("InterruptibleClosureWrapper") != -1);
	}

	public void testWrapInterruptible_NPE1() {
		boolean exCaught = false;
		try {
			InterruptibleClosure<Integer> closure = ClosureTools.wrap((InterruptibleClosure<Integer>) null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testWrapInterruptible_NPE2() {
		InterruptibleClosureWrapper<Integer> closure = ClosureTools.wrap(new LocalInterruptibleClosure());
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
		LocalClosure closure1 = new LocalClosure();
		Closure<Integer> closure2 = ClosureTools.safeClosure(closure1);
		closure2.execute(Integer.valueOf(3));
		assertEquals(3, closure1.count);
		CollectingExceptionHandler handler = new CollectingExceptionHandler();
		closure2 = ClosureTools.safeClosure(closure1, handler);
		closure2.execute(null);
		assertEquals(1, IterableTools.size(handler.getExceptions()));
	}

	public void testSafeClosure_toString() {
		Closure<Integer> closure = ClosureTools.safeClosure(new LocalClosure());
		assertTrue(closure.toString().indexOf("SafeClosureWrapper") != -1);
		assertTrue(closure.toString().indexOf("LocalClosure") != -1);
	}

	public void testSafeClosure_NPE1() {
		boolean exCaught = false;
		try {
			Closure<Integer> closure = ClosureTools.safeClosure((Closure<Integer>) null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testSafeClosure_NPE2() {
		boolean exCaught = false;
		try {
			Closure<Integer> closure = ClosureTools.safeClosure(new LocalClosure(), null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testSafeInterruptibleClosure_execute() throws Exception {
		LocalInterruptibleClosure closure1 = new LocalInterruptibleClosure();
		InterruptibleClosure<Integer> closure2 = ClosureTools.safeClosure(closure1);
		closure2.execute(Integer.valueOf(3));
		assertEquals(3, closure1.count);
		CollectingExceptionHandler handler = new CollectingExceptionHandler();
		closure2 = ClosureTools.safeClosure(closure1, handler);
		closure2.execute(null);
		assertEquals(1, IterableTools.size(handler.getExceptions()));
	}

	public void testSafeInterruptibleClosure_toString() {
		InterruptibleClosure<Integer> closure = ClosureTools.safeClosure(new LocalInterruptibleClosure());
		assertTrue(closure.toString().indexOf("SafeInterruptibleClosureWrapper") != -1);
		assertTrue(closure.toString().indexOf("LocalInterruptibleClosure") != -1);
	}

	public void testSafeInterruptibleClosure_NPE1() {
		boolean exCaught = false;
		try {
			InterruptibleClosure<Integer> closure = ClosureTools.safeClosure((InterruptibleClosure<Integer>) null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testSafeInterruptibleClosure_NPE2() {
		boolean exCaught = false;
		try {
			InterruptibleClosure<Integer> closure = ClosureTools.safeClosure(new LocalInterruptibleClosure(), null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testMethodClosure_execute() throws Exception {
		Closure<Object> closure = ClosureTools.methodClosure("execute");
		LocalCommand cmd = new LocalCommand();
		assertEquals(0, cmd.count);
		closure.execute(cmd);
		assertEquals(1, cmd.count);
	}

	public void testMethodClosure_execute_arg() throws Exception {
		LocalCommand cmd = new LocalCommand();
		Closure<Object> closure = ClosureTools.methodClosure("equals", Object.class, cmd);
		assertEquals(0, cmd.count);
		closure.execute(cmd);
		assertEquals(0, cmd.count);
	}

	public void testMethodClosure_equals() throws Exception {
		Closure<Object> closure1 = ClosureTools.methodClosure("equals", Object.class, "foo");
		Closure<Object> closure2 = ClosureTools.methodClosure("equals", Object.class, "foo");
		assertFalse(closure1.equals(null));
		assertFalse(closure1.equals("foo"));
		assertTrue(closure1.equals(closure1));
		assertTrue(closure1.equals(closure2));
		closure2 = ClosureTools.methodClosure("equals", Object.class, "bar");
		assertFalse(closure1.equals(closure2));
		closure2 = ClosureTools.methodClosure("equals", String.class, "foo");
		assertFalse(closure1.equals(closure2));
		closure2 = ClosureTools.methodClosure("toString", Object.class, "foo");
		assertFalse(closure1.equals(closure2));
	}

	public void testMethodClosure_hashCode() throws Exception {
		Closure<Object> closure1 = ClosureTools.methodClosure("equals", Object.class, "foo");
		Closure<Object> closure2 = ClosureTools.methodClosure("equals", Object.class, "foo");
		assertEquals(closure1.hashCode(), closure1.hashCode());
		assertEquals(closure1.hashCode(), closure2.hashCode());
	}

	public void testMethodClosure_toString() {
		Closure<Object> closure = ClosureTools.methodClosure("execute");
		assertTrue(closure.toString().indexOf("MethodClosure") != -1);
		assertTrue(closure.toString().indexOf("execute()") != -1);
	}

	public void testMethodClosure_NPE1() {
		boolean exCaught = false;
		try {
			Closure<Object> closure = ClosureTools.methodClosure(null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testMethodClosure_NPE2() {
		boolean exCaught = false;
		try {
			Closure<Object> closure = ClosureTools.methodClosure("execute", (Class[]) null, new Object[0]);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testMethodClosure_NPE2a() {
		boolean exCaught = false;
		try {
			Closure<Object> closure = ClosureTools.methodClosure("execute", new Class[] {Object.class, null}, new Object[2]);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testMethodClosure_NPE3() {
		boolean exCaught = false;
		try {
			Closure<Object> closure = ClosureTools.methodClosure("execute", new Class[0], null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testCompositeClosure_execute1() throws Exception {
		LocalClosure closure1 = new LocalClosure();
		LocalClosure closure2 = new LocalClosure();
		Closure<Integer> closure = ClosureTools.compositeClosure(closure1, closure2);
		closure.execute(Integer.valueOf(3));
		assertEquals(3, closure1.count);
		assertEquals(3, closure2.count);
		@SuppressWarnings("unchecked")
		Closure<Integer>[] closureArray = new Closure[0];
		closure = ClosureTools.compositeClosure(closureArray);
		closure.execute(Integer.valueOf(3));
	}

	public void testCompositeClosure_execute2() throws Exception {
		LocalClosure closure1 = new LocalClosure();
		LocalClosure closure2 = new LocalClosure();
		ArrayList<LocalClosure> list = new ArrayList<>();
		list.add(closure1);
		list.add(closure2);
		Closure<Integer> closure = ClosureTools.compositeClosure(list);
		closure.execute(Integer.valueOf(3));
		assertEquals(3, closure1.count);
		assertEquals(3, closure2.count);
		list.clear();
		closure = ClosureTools.compositeClosure(list);
		closure.execute(Integer.valueOf(3));
	}

	public void testCompositeClosure_toString() {
		LocalClosure closure1 = new LocalClosure();
		LocalClosure closure2 = new LocalClosure();
		Closure<Integer> closure = ClosureTools.compositeClosure(closure1, closure2);
		assertTrue(closure.toString().indexOf("CompositeClosure") != -1);
		assertTrue(closure.toString().indexOf("LocalClosure") != -1);
	}

	public void testCompositeClosure_NPE1() {
		LocalClosure closure1 = new LocalClosure();
		boolean exCaught = false;
		try {
			Closure<Integer> closure = ClosureTools.compositeClosure(closure1, null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testCompositeClosure_NPE2() {
		boolean exCaught = false;
		try {
			Closure<Integer> closure = ClosureTools.compositeClosure((Iterable<Closure<Integer>>) null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testCompositeInterruptibleClosure_execute1() throws Exception {
		LocalInterruptibleClosure closure1 = new LocalInterruptibleClosure();
		LocalInterruptibleClosure closure2 = new LocalInterruptibleClosure();
		InterruptibleClosure<Integer> closure = ClosureTools.compositeInterruptibleClosure(closure1, closure2);
		closure.execute(Integer.valueOf(3));
		assertEquals(3, closure1.count);
		assertEquals(3, closure2.count);
		@SuppressWarnings("unchecked")
		InterruptibleClosure<Integer>[] closureArray = new InterruptibleClosure[0];
		closure = ClosureTools.compositeInterruptibleClosure(closureArray);
		closure.execute(Integer.valueOf(3));
	}

	public void testCompositeInterruptibleClosure_execute2() throws Exception {
		LocalInterruptibleClosure closure1 = new LocalInterruptibleClosure();
		LocalInterruptibleClosure closure2 = new LocalInterruptibleClosure();
		ArrayList<LocalInterruptibleClosure> list = new ArrayList<>();
		list.add(closure1);
		list.add(closure2);
		InterruptibleClosure<Integer> closure = ClosureTools.compositeInterruptibleClosure(list);
		closure.execute(Integer.valueOf(3));
		assertEquals(3, closure1.count);
		assertEquals(3, closure2.count);
		list.clear();
		closure = ClosureTools.compositeInterruptibleClosure(list);
		closure.execute(Integer.valueOf(3));
	}

	public void testCompositeInterruptibleClosure_toString() {
		LocalInterruptibleClosure closure1 = new LocalInterruptibleClosure();
		LocalInterruptibleClosure closure2 = new LocalInterruptibleClosure();
		InterruptibleClosure<Integer> closure = ClosureTools.compositeInterruptibleClosure(closure1, closure2);
		assertTrue(closure.toString().indexOf("CompositeInterruptibleClosure") != -1);
		assertTrue(closure.toString().indexOf("LocalInterruptibleClosure") != -1);
	}

	public void testCompositeInterruptibleClosure_NPE1() {
		LocalInterruptibleClosure closure1 = new LocalInterruptibleClosure();
		boolean exCaught = false;
		try {
			InterruptibleClosure<Integer> closure = ClosureTools.compositeInterruptibleClosure(closure1, null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testCompositeInterruptibleClosure_NPE2() {
		boolean exCaught = false;
		try {
			InterruptibleClosure<Integer> closure = ClosureTools.compositeInterruptibleClosure((Iterable<InterruptibleClosure<Integer>>) null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testConditionalClosure_execute1() throws Exception {
		LocalClosure closure1 = new LocalClosure();
		Predicate<Integer> predicate = new PredicateAdapter<Integer>() {
			@Override
			public boolean evaluate(Integer variable) {
				return variable.intValue() > 3;
			}
		};
		Closure<Integer> closure = ClosureTools.conditionalClosure(predicate, closure1);
		closure.execute(Integer.valueOf(3));
		assertEquals(0, closure1.count);
		closure.execute(Integer.valueOf(4));
		assertEquals(4, closure1.count);
	}

	public void testConditionalClosure_execute2() throws Exception {
		LocalClosure closure1 = new LocalClosure();
		LocalClosure closure2 = new LocalClosure();
		Predicate<Integer> predicate = new PredicateAdapter<Integer>() {
			@Override
			public boolean evaluate(Integer variable) {
				return variable.intValue() > 3;
			}
		};
		Closure<Integer> closure = ClosureTools.conditionalClosure(predicate, closure1, closure2);
		closure.execute(Integer.valueOf(3));
		assertEquals(0, closure1.count);
		assertEquals(3, closure2.count);
		closure.execute(Integer.valueOf(4));
		assertEquals(4, closure1.count);
		assertEquals(3, closure2.count);
	}

	public void testConditionalClosure_toString() {
		LocalClosure closure1 = new LocalClosure();
		LocalClosure closure2 = new LocalClosure();
		Predicate<Integer> predicate = new PredicateAdapter<Integer>() {
			@Override
			public boolean evaluate(Integer variable) {
				return variable.intValue() > 3;
			}
		};
		Closure<Integer> closure = ClosureTools.conditionalClosure(predicate, closure1, closure2);
		assertTrue(closure.toString().indexOf("ConditionalClosure") != -1);
		assertTrue(closure.toString().indexOf("Predicate") != -1);
	}

	public void testConditionalClosure_NPE1() {
		LocalClosure closure1 = new LocalClosure();
		LocalClosure closure2 = new LocalClosure();
		boolean exCaught = false;
		try {
			Closure<Integer> closure = ClosureTools.conditionalClosure(null, closure1, closure2);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testConditionalClosure_NPE2() {
		LocalClosure closure1 = new LocalClosure();
		boolean exCaught = false;
		try {
			Closure<Integer> closure = ClosureTools.conditionalClosure(null, closure1, null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testConditionalClosure_NPE3() {
		LocalClosure closure2 = new LocalClosure();
		boolean exCaught = false;
		try {
			Closure<Integer> closure = ClosureTools.conditionalClosure(PredicateTools.true_(), null, closure2);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testConditionalInterruptibleClosure_execute1() throws Exception {
		LocalInterruptibleClosure closure1 = new LocalInterruptibleClosure();
		Predicate<Integer> predicate = new PredicateAdapter<Integer>() {
			@Override
			public boolean evaluate(Integer variable) {
				return variable.intValue() > 3;
			}
		};
		InterruptibleClosure<Integer> closure = ClosureTools.conditionalClosure(predicate, closure1);
		closure.execute(Integer.valueOf(3));
		assertEquals(0, closure1.count);
		closure.execute(Integer.valueOf(4));
		assertEquals(4, closure1.count);
	}

	public void testConditionalInterruptibleClosure_execute2() throws Exception {
		LocalInterruptibleClosure closure1 = new LocalInterruptibleClosure();
		LocalInterruptibleClosure closure2 = new LocalInterruptibleClosure();
		Predicate<Integer> predicate = new PredicateAdapter<Integer>() {
			@Override
			public boolean evaluate(Integer variable) {
				return variable.intValue() > 3;
			}
		};
		InterruptibleClosure<Integer> closure = ClosureTools.conditionalClosure(predicate, closure1, closure2);
		closure.execute(Integer.valueOf(3));
		assertEquals(0, closure1.count);
		assertEquals(3, closure2.count);
		closure.execute(Integer.valueOf(4));
		assertEquals(4, closure1.count);
		assertEquals(3, closure2.count);
	}

	public void testConditionalInterruptibleClosure_toString() {
		LocalInterruptibleClosure closure1 = new LocalInterruptibleClosure();
		LocalInterruptibleClosure closure2 = new LocalInterruptibleClosure();
		Predicate<Integer> predicate = new PredicateAdapter<Integer>() {
			@Override
			public boolean evaluate(Integer variable) {
				return variable.intValue() > 3;
			}
		};
		InterruptibleClosure<Integer> closure = ClosureTools.conditionalClosure(predicate, closure1, closure2);
		assertTrue(closure.toString().indexOf("ConditionalInterruptibleClosure") != -1);
		assertTrue(closure.toString().indexOf("Predicate") != -1);
	}

	public void testConditionalInterruptibleClosure_NPE1() {
		LocalInterruptibleClosure closure1 = new LocalInterruptibleClosure();
		LocalInterruptibleClosure closure2 = new LocalInterruptibleClosure();
		boolean exCaught = false;
		try {
			InterruptibleClosure<Integer> closure = ClosureTools.conditionalClosure(null, closure1, closure2);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testConditionalInterruptibleClosure_NPE2() {
		LocalInterruptibleClosure closure1 = new LocalInterruptibleClosure();
		boolean exCaught = false;
		try {
			InterruptibleClosure<Integer> closure = ClosureTools.conditionalClosure(null, closure1, null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testConditionalInterruptibleClosure_NPE3() {
		LocalInterruptibleClosure closure2 = new LocalInterruptibleClosure();
		boolean exCaught = false;
		try {
			InterruptibleClosure<Integer> closure = ClosureTools.conditionalClosure(PredicateTools.true_(), null, closure2);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testSwitchClosure_execute1() throws Exception {
		List<Association<Predicate<Integer>, LocalClosure>> list = new ArrayList<>();

		Predicate<Integer> predicate1 = PredicateTools.isEqual(Integer.valueOf(1));
		LocalClosure closure1 = new LocalClosure();
		Association<Predicate<Integer>, LocalClosure> association1 = new SimpleAssociation<>(predicate1, closure1);
		list.add(association1);

		Predicate<Integer> predicate2 = PredicateTools.isEqual(Integer.valueOf(2));
		LocalClosure closure2 = new LocalClosure();
		Association<Predicate<Integer>, LocalClosure> association2 = new SimpleAssociation<>(predicate2, closure2);
		list.add(association2);

		Predicate<Integer> predicate3 = PredicateTools.isEqual(Integer.valueOf(3));
		LocalClosure closure3 = new LocalClosure();
		Association<Predicate<Integer>, LocalClosure> association3 = new SimpleAssociation<>(predicate3, closure3);
		list.add(association3);

		Closure<Integer> closure = ClosureTools.switchClosure(list);
		assertEquals(0, closure1.count);
		assertEquals(0, closure2.count);
		assertEquals(0, closure3.count);

		closure.execute(Integer.valueOf(0));
		assertEquals(0, closure1.count);
		assertEquals(0, closure2.count);
		assertEquals(0, closure3.count);

		closure.execute(Integer.valueOf(4));
		assertEquals(0, closure1.count);
		assertEquals(0, closure2.count);
		assertEquals(0, closure3.count);

		closure.execute(Integer.valueOf(1));
		assertEquals(1, closure1.count);
		assertEquals(0, closure2.count);
		assertEquals(0, closure3.count);

		closure.execute(Integer.valueOf(2));
		assertEquals(1, closure1.count);
		assertEquals(2, closure2.count);
		assertEquals(0, closure3.count);

		closure.execute(Integer.valueOf(2));
		assertEquals(1, closure1.count);
		assertEquals(4, closure2.count);
		assertEquals(0, closure3.count);

		closure.execute(Integer.valueOf(3));
		assertEquals(1, closure1.count);
		assertEquals(4, closure2.count);
		assertEquals(3, closure3.count);
	}

	public void testSwitchClosure_execute2() throws Exception {
		List<Association<Predicate<Integer>, LocalClosure>> list = new ArrayList<>();

		Predicate<Integer> predicate1 = PredicateTools.isEqual(Integer.valueOf(1));
		LocalClosure closure1 = new LocalClosure();
		Association<Predicate<Integer>, LocalClosure> association1 = new SimpleAssociation<>(predicate1, closure1);
		list.add(association1);

		Predicate<Integer> predicate2 = PredicateTools.isEqual(Integer.valueOf(2));
		LocalClosure closure2 = new LocalClosure();
		Association<Predicate<Integer>, LocalClosure> association2 = new SimpleAssociation<>(predicate2, closure2);
		list.add(association2);

		Predicate<Integer> predicate3 = PredicateTools.isEqual(Integer.valueOf(3));
		LocalClosure closure3 = new LocalClosure();
		Association<Predicate<Integer>, LocalClosure> association3 = new SimpleAssociation<>(predicate3, closure3);
		list.add(association3);

		LocalClosure closureX = new LocalClosure();

		Closure<Integer> closure = ClosureTools.switchClosure(list, closureX);
		assertEquals(0, closure1.count);
		assertEquals(0, closure2.count);
		assertEquals(0, closure3.count);
		assertEquals(0, closureX.count);

		closure.execute(Integer.valueOf(0));
		assertEquals(0, closure1.count);
		assertEquals(0, closure2.count);
		assertEquals(0, closure3.count);
		assertEquals(0, closureX.count);

		closure.execute(Integer.valueOf(4));
		assertEquals(0, closure1.count);
		assertEquals(0, closure2.count);
		assertEquals(0, closure3.count);
		assertEquals(4, closureX.count);

		closure.execute(Integer.valueOf(1));
		assertEquals(1, closure1.count);
		assertEquals(0, closure2.count);
		assertEquals(0, closure3.count);
		assertEquals(4, closureX.count);

		closure.execute(Integer.valueOf(2));
		assertEquals(1, closure1.count);
		assertEquals(2, closure2.count);
		assertEquals(0, closure3.count);
		assertEquals(4, closureX.count);

		closure.execute(Integer.valueOf(2));
		assertEquals(1, closure1.count);
		assertEquals(4, closure2.count);
		assertEquals(0, closure3.count);
		assertEquals(4, closureX.count);

		closure.execute(Integer.valueOf(3));
		assertEquals(1, closure1.count);
		assertEquals(4, closure2.count);
		assertEquals(3, closure3.count);
		assertEquals(4, closureX.count);

		closure.execute(Integer.valueOf(-7));
		assertEquals(1, closure1.count);
		assertEquals(4, closure2.count);
		assertEquals(3, closure3.count);
		assertEquals(-3, closureX.count);
	}

	public void testSwitchClosure_toString() {
		List<Association<Predicate<Integer>, LocalClosure>> list = new ArrayList<>();

		Predicate<Integer> predicate1 = PredicateTools.isEqual(Integer.valueOf(1));
		LocalClosure closure1 = new LocalClosure();
		Association<Predicate<Integer>, LocalClosure> association1 = new SimpleAssociation<>(predicate1, closure1);
		list.add(association1);

		Predicate<Integer> predicate2 = PredicateTools.isEqual(Integer.valueOf(2));
		LocalClosure closure2 = new LocalClosure();
		Association<Predicate<Integer>, LocalClosure> association2 = new SimpleAssociation<>(predicate2, closure2);
		list.add(association2);

		Predicate<Integer> predicate3 = PredicateTools.isEqual(Integer.valueOf(3));
		LocalClosure closure3 = new LocalClosure();
		Association<Predicate<Integer>, LocalClosure> association3 = new SimpleAssociation<>(predicate3, closure3);
		list.add(association3);

		LocalClosure closureX = new LocalClosure();

		Closure<Integer> closure = ClosureTools.switchClosure(list, closureX);

		assertTrue(closure.toString().indexOf("SwitchClosure") != -1);
		assertTrue(closure.toString().indexOf("LocalClosure") != -1);
	}

	public void testSwitchClosure_NPE1() {
		List<Association<Predicate<Integer>, LocalClosure>> list = new ArrayList<>();

		Predicate<Integer> predicate1 = PredicateTools.isEqual(Integer.valueOf(1));
		LocalClosure closure1 = new LocalClosure();
		Association<Predicate<Integer>, LocalClosure> association1 = new SimpleAssociation<>(predicate1, closure1);
		list.add(association1);

		Predicate<Integer> predicate2 = PredicateTools.isEqual(Integer.valueOf(2));
		LocalClosure closure2 = new LocalClosure();
		Association<Predicate<Integer>, LocalClosure> association2 = new SimpleAssociation<>(predicate2, closure2);
		list.add(association2);

		Predicate<Integer> predicate3 = PredicateTools.isEqual(Integer.valueOf(3));
		LocalClosure closure3 = new LocalClosure();
		Association<Predicate<Integer>, LocalClosure> association3 = new SimpleAssociation<>(predicate3, closure3);
		list.add(association3);

		boolean exCaught = false;
		try {
			Closure<Integer> closure = ClosureTools.switchClosure(list, null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testSwitchClosure_NPE2() {
		List<Association<Predicate<Integer>, LocalClosure>> list = new ArrayList<>();

		Predicate<Integer> predicate1 = PredicateTools.isEqual(Integer.valueOf(1));
		LocalClosure closure1 = new LocalClosure();
		Association<Predicate<Integer>, LocalClosure> association1 = new SimpleAssociation<>(predicate1, closure1);
		list.add(association1);

		Predicate<Integer> predicate2 = PredicateTools.isEqual(Integer.valueOf(2));
		LocalClosure closure2 = new LocalClosure();
		Association<Predicate<Integer>, LocalClosure> association2 = new SimpleAssociation<>(predicate2, closure2);
		list.add(association2);

		Predicate<Integer> predicate3 = PredicateTools.isEqual(Integer.valueOf(3));
		LocalClosure closure3 = new LocalClosure();
		Association<Predicate<Integer>, LocalClosure> association3 = new SimpleAssociation<>(predicate3, closure3);
		list.add(association3);
		list.add(null);

		boolean exCaught = false;
		try {
			Closure<Integer> closure = ClosureTools.switchClosure(list);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testSwitchClosure_NPE3() {
		boolean exCaught = false;
		try {
			Closure<Integer> closure = ClosureTools.switchClosure(null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testSwitchInterruptibleClosure_execute1() throws Exception {
		List<Association<Predicate<Integer>, LocalInterruptibleClosure>> list = new ArrayList<>();

		Predicate<Integer> predicate1 = PredicateTools.isEqual(Integer.valueOf(1));
		LocalInterruptibleClosure closure1 = new LocalInterruptibleClosure();
		Association<Predicate<Integer>, LocalInterruptibleClosure> association1 = new SimpleAssociation<>(predicate1, closure1);
		list.add(association1);

		Predicate<Integer> predicate2 = PredicateTools.isEqual(Integer.valueOf(2));
		LocalInterruptibleClosure closure2 = new LocalInterruptibleClosure();
		Association<Predicate<Integer>, LocalInterruptibleClosure> association2 = new SimpleAssociation<>(predicate2, closure2);
		list.add(association2);

		Predicate<Integer> predicate3 = PredicateTools.isEqual(Integer.valueOf(3));
		LocalInterruptibleClosure closure3 = new LocalInterruptibleClosure();
		Association<Predicate<Integer>, LocalInterruptibleClosure> association3 = new SimpleAssociation<>(predicate3, closure3);
		list.add(association3);

		InterruptibleClosure<Integer> closure = ClosureTools.switchInterruptibleClosure(list);
		assertEquals(0, closure1.count);
		assertEquals(0, closure2.count);
		assertEquals(0, closure3.count);

		closure.execute(Integer.valueOf(0));
		assertEquals(0, closure1.count);
		assertEquals(0, closure2.count);
		assertEquals(0, closure3.count);

		closure.execute(Integer.valueOf(4));
		assertEquals(0, closure1.count);
		assertEquals(0, closure2.count);
		assertEquals(0, closure3.count);

		closure.execute(Integer.valueOf(1));
		assertEquals(1, closure1.count);
		assertEquals(0, closure2.count);
		assertEquals(0, closure3.count);

		closure.execute(Integer.valueOf(2));
		assertEquals(1, closure1.count);
		assertEquals(2, closure2.count);
		assertEquals(0, closure3.count);

		closure.execute(Integer.valueOf(2));
		assertEquals(1, closure1.count);
		assertEquals(4, closure2.count);
		assertEquals(0, closure3.count);

		closure.execute(Integer.valueOf(3));
		assertEquals(1, closure1.count);
		assertEquals(4, closure2.count);
		assertEquals(3, closure3.count);
	}

	public void testSwitchInterruptibleClosure_execute2() throws Exception {
		List<Association<Predicate<Integer>, LocalInterruptibleClosure>> list = new ArrayList<>();

		Predicate<Integer> predicate1 = PredicateTools.isEqual(Integer.valueOf(1));
		LocalInterruptibleClosure closure1 = new LocalInterruptibleClosure();
		Association<Predicate<Integer>, LocalInterruptibleClosure> association1 = new SimpleAssociation<>(predicate1, closure1);
		list.add(association1);

		Predicate<Integer> predicate2 = PredicateTools.isEqual(Integer.valueOf(2));
		LocalInterruptibleClosure closure2 = new LocalInterruptibleClosure();
		Association<Predicate<Integer>, LocalInterruptibleClosure> association2 = new SimpleAssociation<>(predicate2, closure2);
		list.add(association2);

		Predicate<Integer> predicate3 = PredicateTools.isEqual(Integer.valueOf(3));
		LocalInterruptibleClosure closure3 = new LocalInterruptibleClosure();
		Association<Predicate<Integer>, LocalInterruptibleClosure> association3 = new SimpleAssociation<>(predicate3, closure3);
		list.add(association3);

		LocalInterruptibleClosure closureX = new LocalInterruptibleClosure();

		InterruptibleClosure<Integer> closure = ClosureTools.switchInterruptibleClosure(list, closureX);
		assertEquals(0, closure1.count);
		assertEquals(0, closure2.count);
		assertEquals(0, closure3.count);
		assertEquals(0, closureX.count);

		closure.execute(Integer.valueOf(0));
		assertEquals(0, closure1.count);
		assertEquals(0, closure2.count);
		assertEquals(0, closure3.count);
		assertEquals(0, closureX.count);

		closure.execute(Integer.valueOf(4));
		assertEquals(0, closure1.count);
		assertEquals(0, closure2.count);
		assertEquals(0, closure3.count);
		assertEquals(4, closureX.count);

		closure.execute(Integer.valueOf(1));
		assertEquals(1, closure1.count);
		assertEquals(0, closure2.count);
		assertEquals(0, closure3.count);
		assertEquals(4, closureX.count);

		closure.execute(Integer.valueOf(2));
		assertEquals(1, closure1.count);
		assertEquals(2, closure2.count);
		assertEquals(0, closure3.count);
		assertEquals(4, closureX.count);

		closure.execute(Integer.valueOf(2));
		assertEquals(1, closure1.count);
		assertEquals(4, closure2.count);
		assertEquals(0, closure3.count);
		assertEquals(4, closureX.count);

		closure.execute(Integer.valueOf(3));
		assertEquals(1, closure1.count);
		assertEquals(4, closure2.count);
		assertEquals(3, closure3.count);
		assertEquals(4, closureX.count);

		closure.execute(Integer.valueOf(-7));
		assertEquals(1, closure1.count);
		assertEquals(4, closure2.count);
		assertEquals(3, closure3.count);
		assertEquals(-3, closureX.count);
	}

	public void testSwitchInterruptibleClosure_toString() {
		List<Association<Predicate<Integer>, LocalInterruptibleClosure>> list = new ArrayList<>();

		Predicate<Integer> predicate1 = PredicateTools.isEqual(Integer.valueOf(1));
		LocalInterruptibleClosure closure1 = new LocalInterruptibleClosure();
		Association<Predicate<Integer>, LocalInterruptibleClosure> association1 = new SimpleAssociation<>(predicate1, closure1);
		list.add(association1);

		Predicate<Integer> predicate2 = PredicateTools.isEqual(Integer.valueOf(2));
		LocalInterruptibleClosure closure2 = new LocalInterruptibleClosure();
		Association<Predicate<Integer>, LocalInterruptibleClosure> association2 = new SimpleAssociation<>(predicate2, closure2);
		list.add(association2);

		Predicate<Integer> predicate3 = PredicateTools.isEqual(Integer.valueOf(3));
		LocalInterruptibleClosure closure3 = new LocalInterruptibleClosure();
		Association<Predicate<Integer>, LocalInterruptibleClosure> association3 = new SimpleAssociation<>(predicate3, closure3);
		list.add(association3);

		LocalInterruptibleClosure closureX = new LocalInterruptibleClosure();

		InterruptibleClosure<Integer> closure = ClosureTools.switchInterruptibleClosure(list, closureX);

		assertTrue(closure.toString().indexOf("SwitchInterruptibleClosure") != -1);
		assertTrue(closure.toString().indexOf("LocalInterruptibleClosure") != -1);
	}

	public void testSwitchInterruptibleClosure_NPE1() {
		List<Association<Predicate<Integer>, LocalInterruptibleClosure>> list = new ArrayList<>();

		Predicate<Integer> predicate1 = PredicateTools.isEqual(Integer.valueOf(1));
		LocalInterruptibleClosure closure1 = new LocalInterruptibleClosure();
		Association<Predicate<Integer>, LocalInterruptibleClosure> association1 = new SimpleAssociation<>(predicate1, closure1);
		list.add(association1);

		Predicate<Integer> predicate2 = PredicateTools.isEqual(Integer.valueOf(2));
		LocalInterruptibleClosure closure2 = new LocalInterruptibleClosure();
		Association<Predicate<Integer>, LocalInterruptibleClosure> association2 = new SimpleAssociation<>(predicate2, closure2);
		list.add(association2);

		Predicate<Integer> predicate3 = PredicateTools.isEqual(Integer.valueOf(3));
		LocalInterruptibleClosure closure3 = new LocalInterruptibleClosure();
		Association<Predicate<Integer>, LocalInterruptibleClosure> association3 = new SimpleAssociation<>(predicate3, closure3);
		list.add(association3);

		boolean exCaught = false;
		try {
			InterruptibleClosure<Integer> closure = ClosureTools.switchInterruptibleClosure(list, null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testSwitchInterruptibleClosure_NPE2() {
		List<Association<Predicate<Integer>, LocalInterruptibleClosure>> list = new ArrayList<>();

		Predicate<Integer> predicate1 = PredicateTools.isEqual(Integer.valueOf(1));
		LocalInterruptibleClosure closure1 = new LocalInterruptibleClosure();
		Association<Predicate<Integer>, LocalInterruptibleClosure> association1 = new SimpleAssociation<>(predicate1, closure1);
		list.add(association1);

		Predicate<Integer> predicate2 = PredicateTools.isEqual(Integer.valueOf(2));
		LocalInterruptibleClosure closure2 = new LocalInterruptibleClosure();
		Association<Predicate<Integer>, LocalInterruptibleClosure> association2 = new SimpleAssociation<>(predicate2, closure2);
		list.add(association2);

		Predicate<Integer> predicate3 = PredicateTools.isEqual(Integer.valueOf(3));
		LocalInterruptibleClosure closure3 = new LocalInterruptibleClosure();
		Association<Predicate<Integer>, LocalInterruptibleClosure> association3 = new SimpleAssociation<>(predicate3, closure3);
		list.add(association3);
		list.add(null);

		boolean exCaught = false;
		try {
			InterruptibleClosure<Integer> closure = ClosureTools.switchInterruptibleClosure(list);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testSwitchInterruptibleClosure_NPE3() {
		boolean exCaught = false;
		try {
			InterruptibleClosure<Integer> closure = ClosureTools.switchInterruptibleClosure(null);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testDisabledClosure_execute() {
		Closure<Integer> closure = ClosureTools.disabledClosure();
		boolean exCaught = false;
		try {
			closure.execute(null);
			fail();
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testDisabledClosure_toString() {
		Closure<Integer> closure = ClosureTools.disabledClosure();
		assertTrue(closure.toString().indexOf("DisabledClosure") != -1);
	}

	public void testDisabledClosure_serialization() throws Exception {
		Closure<Integer> closure = ClosureTools.disabledClosure();
		assertSame(closure, TestTools.serialize(closure));
	}

	public void testInterruptedClosure_execute() throws Exception {
		InterruptibleClosure<Integer> closure = ClosureTools.interruptedClosure();
		boolean exCaught = false;
		try {
			closure.execute(null);
			fail();
		} catch (InterruptedException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testInterruptedClosure_toString() {
		InterruptibleClosure<Integer> closure = ClosureTools.interruptedClosure();
		assertTrue(closure.toString().indexOf("InterruptedClosure") != -1);
	}

	public void testInterruptedClosure_serialization() throws Exception {
		InterruptibleClosure<Integer> closure = ClosureTools.interruptedClosure();
		assertSame(closure, TestTools.serialize(closure));
	}

	public void testRepeatingClosure_execute1() throws Exception {
		LocalClosure closure1 = new LocalClosure();
		Closure<Integer> closure2 = ClosureTools.repeatingClosure(closure1, 3);
		closure2.execute(Integer.valueOf(3));
		assertEquals(9, closure1.count);
	}

	public void testRepeatingClosure_execute2() throws Exception {
		LocalClosure closure1 = new LocalClosure();
		Closure<Integer> closure2 = ClosureTools.repeatingClosure(closure1, 0);
		closure2.execute(Integer.valueOf(3));
		assertEquals(0, closure1.count);
	}

	public void testRepeatingClosure_toString() {
		LocalClosure closure1 = new LocalClosure();
		Closure<Integer> closure2 = ClosureTools.repeatingClosure(closure1, 3);
		assertTrue(closure2.toString().indexOf("RepeatingClosure") != -1);
		assertTrue(closure2.toString().indexOf("LocalClosure") != -1);
	}

	public void testRepeatingClosure_NPE1() {
		boolean exCaught = false;
		try {
			Closure<Integer> closure = ClosureTools.repeatingClosure((Closure<Integer>) null, 3);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRepeatingClosure_NPE2() {
		LocalClosure closure1 = new LocalClosure();
		boolean exCaught = false;
		try {
			Closure<Integer> closure2 = ClosureTools.repeatingClosure(closure1, -3);
			fail("bogus: " + closure2);
		} catch (IndexOutOfBoundsException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRepeatingInterruptibleClosure_execute1() throws Exception {
		LocalInterruptibleClosure closure1 = new LocalInterruptibleClosure();
		InterruptibleClosure<Integer> closure2 = ClosureTools.repeatingClosure(closure1, 3);
		closure2.execute(Integer.valueOf(3));
		assertEquals(9, closure1.count);
	}

	public void testRepeatingInterruptibleClosure_execute2() throws Exception {
		LocalInterruptibleClosure closure1 = new LocalInterruptibleClosure();
		InterruptibleClosure<Integer> closure2 = ClosureTools.repeatingClosure(closure1, 0);
		closure2.execute(Integer.valueOf(3));
		assertEquals(0, closure1.count);
	}

	public void testRepeatingInterruptibleClosure_toString() {
		LocalInterruptibleClosure closure1 = new LocalInterruptibleClosure();
		InterruptibleClosure<Integer> closure2 = ClosureTools.repeatingClosure(closure1, 3);
		assertTrue(closure2.toString().indexOf("RepeatingInterruptibleClosure") != -1);
		assertTrue(closure2.toString().indexOf("LocalInterruptibleClosure") != -1);
	}

	public void testRepeatingInterruptibleClosure_NPE1() {
		boolean exCaught = false;
		try {
			InterruptibleClosure<Integer> closure = ClosureTools.repeatingClosure((InterruptibleClosure<Integer>) null, 3);
			fail("bogus: " + closure);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRepeatingInterruptibleClosure_NPE2() {
		LocalInterruptibleClosure closure1 = new LocalInterruptibleClosure();
		boolean exCaught = false;
		try {
			InterruptibleClosure<Integer> closure2 = ClosureTools.repeatingClosure(closure1, -3);
			fail("bogus: " + closure2);
		} catch (IndexOutOfBoundsException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testWhileClosure_execute() throws Exception {
		ModifiableIntReference ref = new SimpleIntReference(0);
		IntRefClosure closure1 = new IntRefClosure();
		Predicate<ModifiableIntReference> predicate = new IntRefValueNotEqualPredicate(3);
		Closure<ModifiableIntReference> closure2 = ClosureTools.whileClosure(predicate, closure1);
		closure2.execute(ref);
		assertEquals(6, closure1.count);
		ref.setValue(-6);
		closure2.execute(ref);
		assertEquals(24, closure1.count);
	}

	public void testWhileClosure_toString() {
		IntRefClosure closure1 = new IntRefClosure();
		Predicate<ModifiableIntReference> predicate = new IntRefValueNotEqualPredicate(3);
		Closure<ModifiableIntReference> closure2 = ClosureTools.whileClosure(predicate, closure1);
		assertTrue(closure2.toString().indexOf("WhileClosure") != -1);
		assertTrue(closure2.toString().indexOf("IntRefValueNotEqualPredicate") != -1);
	}

	public void testWhileClosure_NPE1() {
		IntRefClosure closure1 = new IntRefClosure();
		boolean exCaught = false;
		try {
			Closure<ModifiableIntReference> closure2 = ClosureTools.whileClosure(null, closure1);
			fail("bogus: " + closure2);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testWhileClosure_NPE2() {
		Predicate<ModifiableIntReference> predicate = new IntRefValueNotEqualPredicate(3);
		boolean exCaught = false;
		try {
			Closure<ModifiableIntReference> closure2 = ClosureTools.whileClosure(predicate, (IntRefClosure) null);
			fail("bogus: " + closure2);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public static class IntRefClosure
		extends ClosureAdapter<ModifiableIntReference>
	{
		public int count = 0;
		@Override
		public void execute(ModifiableIntReference argument) {
			this.count += 2;
			argument.increment();
		}
	}

	public void testWhileInterruptibleClosure_execute() throws Exception {
		ModifiableIntReference ref = new SimpleIntReference(0);
		IntRefInterruptibleClosure closure1 = new IntRefInterruptibleClosure();
		Predicate<ModifiableIntReference> predicate = new IntRefValueNotEqualPredicate(3);
		InterruptibleClosure<ModifiableIntReference> closure2 = ClosureTools.whileClosure(predicate, closure1);
		closure2.execute(ref);
		assertEquals(6, closure1.count);
		ref.setValue(-6);
		closure2.execute(ref);
		assertEquals(24, closure1.count);
	}

	public void testWhileInterruptibleClosure_toString() {
		IntRefInterruptibleClosure closure1 = new IntRefInterruptibleClosure();
		Predicate<ModifiableIntReference> predicate = new IntRefValueNotEqualPredicate(3);
		InterruptibleClosure<ModifiableIntReference> closure2 = ClosureTools.whileClosure(predicate, closure1);
		assertTrue(closure2.toString().indexOf("WhileInterruptibleClosure") != -1);
		assertTrue(closure2.toString().indexOf("IntRefValueNotEqualPredicate") != -1);
	}

	public void testWhileInterruptibleClosure_NPE1() {
		IntRefInterruptibleClosure closure1 = new IntRefInterruptibleClosure();
		boolean exCaught = false;
		try {
			InterruptibleClosure<ModifiableIntReference> closure2 = ClosureTools.whileClosure(null, closure1);
			fail("bogus: " + closure2);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testWhileInterruptibleClosure_NPE2() {
		Predicate<ModifiableIntReference> predicate = new IntRefValueNotEqualPredicate(3);
		boolean exCaught = false;
		try {
			InterruptibleClosure<ModifiableIntReference> closure2 = ClosureTools.whileClosure(predicate, (IntRefInterruptibleClosure) null);
			fail("bogus: " + closure2);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public static class IntRefInterruptibleClosure
		extends InterruptibleClosureAdapter<ModifiableIntReference>
	{
		public int count = 0;
		@Override
		public void execute(ModifiableIntReference argument) throws InterruptedException {
			this.count += 2;
			argument.increment();
		}
	}

	public static class IntRefValueNotEqualPredicate
		extends PredicateAdapter<ModifiableIntReference>
	{
		private final int count;
		public IntRefValueNotEqualPredicate(int count) {
			super();
			this.count = count;
		}
		@Override
		public boolean evaluate(ModifiableIntReference variable) {
			return variable.notEqual(this.count);
		}
	}

	public void testUntilClosure_execute() throws Exception {
		ModifiableIntReference ref = new SimpleIntReference(0);
		IntRefClosure closure1 = new IntRefClosure();
		Predicate<ModifiableIntReference> predicate = new IntRefValueEqualPredicate(3);
		Closure<ModifiableIntReference> closure2 = ClosureTools.untilClosure(closure1, predicate);
		closure2.execute(ref);
		assertEquals(6, closure1.count);
		ref.setValue(-6);
		closure2.execute(ref);
		assertEquals(24, closure1.count);
	}

	public void testUntilClosure_toString() {
		IntRefClosure closure1 = new IntRefClosure();
		Predicate<ModifiableIntReference> predicate = new IntRefValueEqualPredicate(3);
		Closure<ModifiableIntReference> closure2 = ClosureTools.untilClosure(closure1, predicate);
		assertTrue(closure2.toString().indexOf("UntilClosure") != -1);
		assertTrue(closure2.toString().indexOf("IntRefValueEqualPredicate") != -1);
	}

	public void testUntilClosure_NPE1() {
		IntRefClosure closure1 = new IntRefClosure();
		boolean exCaught = false;
		try {
			Closure<ModifiableIntReference> closure2 = ClosureTools.untilClosure(closure1, null);
			fail("bogus: " + closure2);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testUntilClosure_NPE2() {
		Predicate<ModifiableIntReference> predicate = new IntRefValueEqualPredicate(3);
		boolean exCaught = false;
		try {
			Closure<ModifiableIntReference> closure2 = ClosureTools.untilClosure((IntRefClosure) null, predicate);
			fail("bogus: " + closure2);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testUntilInterruptibleClosure_execute() throws Exception {
		ModifiableIntReference ref = new SimpleIntReference(0);
		IntRefInterruptibleClosure closure1 = new IntRefInterruptibleClosure();
		Predicate<ModifiableIntReference> predicate = new IntRefValueEqualPredicate(3);
		InterruptibleClosure<ModifiableIntReference> closure2 = ClosureTools.untilClosure(closure1, predicate);
		closure2.execute(ref);
		assertEquals(6, closure1.count);
		ref.setValue(-6);
		closure2.execute(ref);
		assertEquals(24, closure1.count);
	}

	public void testUntilInterruptibleClosure_toString() {
		IntRefInterruptibleClosure closure1 = new IntRefInterruptibleClosure();
		Predicate<ModifiableIntReference> predicate = new IntRefValueEqualPredicate(3);
		InterruptibleClosure<ModifiableIntReference> closure2 = ClosureTools.untilClosure(closure1, predicate);
		assertTrue(closure2.toString().indexOf("UntilInterruptibleClosure") != -1);
		assertTrue(closure2.toString().indexOf("IntRefValueEqualPredicate") != -1);
	}

	public void testUntilInterruptibleClosure_NPE1() {
		IntRefInterruptibleClosure closure1 = new IntRefInterruptibleClosure();
		boolean exCaught = false;
		try {
			InterruptibleClosure<ModifiableIntReference> closure2 = ClosureTools.untilClosure(closure1, null);
			fail("bogus: " + closure2);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testUntilInterruptibleClosure_NPE2() {
		Predicate<ModifiableIntReference> predicate = new IntRefValueEqualPredicate(3);
		boolean exCaught = false;
		try {
			InterruptibleClosure<ModifiableIntReference> closure2 = ClosureTools.untilClosure((IntRefInterruptibleClosure) null, predicate);
			fail("bogus: " + closure2);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public static class IntRefValueEqualPredicate
		extends PredicateAdapter<ModifiableIntReference>
	{
		private final int count;
		public IntRefValueEqualPredicate(int count) {
			super();
			this.count = count;
		}
		@Override
		public boolean evaluate(ModifiableIntReference variable) {
			return variable.equals(this.count);
		}
	}

	public void testConstructor() {
		boolean exCaught = false;
		try {
			Object at = ClassTools.newInstance(ClosureTools.class);
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

	public static class LocalClosure
		extends ClosureAdapter<Integer>
	{
		public int count = 0;

		@Override
		public void execute(Integer argument) {
			this.count += argument.intValue(); // possible NPE
		}
	}

	public static class LocalClosure2
		extends ClosureAdapter<Integer>
	{
		public int count = 0;

		@Override
		public void execute(Integer argument) {
			this.count += (2 * argument.intValue()); // possible NPE
		}
	}

	public static class LocalInterruptibleClosure
		extends InterruptibleClosureAdapter<Integer>
	{
		public int count = 0;

		@Override
		public void execute(Integer argument) throws InterruptedException {
			this.count += argument.intValue(); // possible NPE
		}
	}

	public static class LocalCommand
		extends CommandAdapter
	{
		public int count = 0;

		@Override
		public void execute() {
			this.count++;
		}
	}

	public static class LocalInterruptibleCommand
		extends InterruptibleCommandAdapter
	{
		public int count = 0;

		@Override
		public void execute() {
			this.count++;
		}
	}

	public static class LocalFactory<T>
		extends FactoryAdapter<T>
	{
		public int count = 0;

		@Override
		public T create() {
			this.count++;
			return null;
		}
	}

	public static class LocalInterruptibleFactory<T>
		extends InterruptibleFactoryAdapter<T>
	{
		public int count = 0;

		@Override
		public T create() throws InterruptedException {
			this.count++;
			return null;
		}
	}

	public static class LocalTransformer<I, O>
		extends TransformerAdapter<I, O>
	{
		public int count = 0;

		@Override
		public O transform(I input) {
			this.count++;
			return null;
		}
	}

	public static class LocalInterruptibleTransformer<I, O>
		extends InterruptibleTransformerAdapter<I, O>
	{
		public int count = 0;

		@Override
		public O transform(I input) throws InterruptedException {
			this.count++;
			return null;
		}
	}
}
