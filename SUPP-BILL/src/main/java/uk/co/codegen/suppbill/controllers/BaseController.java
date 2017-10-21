package uk.co.codegen.suppbill.controllers;

public abstract class BaseController
{
	protected void handleErrors( String msg) throws Exception
    {
        throw new Exception();
    }

}
