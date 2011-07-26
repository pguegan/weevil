package com.sqli.can.weevil;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.sqli.can.weevil.collections.ArrayTest;
import com.sqli.can.weevil.collections.CoupleTest;
import com.sqli.can.weevil.collections.operators.ChainCollectorTest;
import com.sqli.can.weevil.collections.operators.StringCollectorTest;
import com.sqli.can.weevil.graphs.trees.NodeTest;
import com.sqli.can.weevil.graphs.trees.TreeTest;
import com.sqli.can.weevil.money.AmountTest;
import com.sqli.can.weevil.text.ChainBuilderTest;
import com.sqli.can.weevil.text.ChainTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
    ArrayTest.class, 
    CoupleTest.class,
    AmountTest.class, 
    ChainTest.class,
    ChainBuilderTest.class,
    NodeTest.class,
    TreeTest.class,
    StringCollectorTest.class,
    ChainCollectorTest.class})
public class AllTests {}
