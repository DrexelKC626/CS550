/* This is the lex file for the sample mini language.
 */

import java_cup.runtime.Symbol;
%%
%cup
%%
";" {return new Symbol(sym.SEMI); }
"+" {return new Symbol(sym.PLUS); }
"-" {return new Symbol(sym.MINUS); }
"*" {return new Symbol(sym.TIMES); }
"," {return new Symbol(sym.COMMA); }
":=" {return new Symbol(sym.ASSIGN); }
"define" {return new Symbol(sym.DEFINE); }
"(" {return new Symbol(sym.LPAREN); }
")" {return new Symbol(sym.RPAREN); }
"[" {return new Symbol(sym.LSQURE); }
"]" {return new Symbol(sym.RSQURE); }
"if" {return new Symbol(sym.IF); }
"||" {return new Symbol(sym.OR); }
"then" {return new Symbol(sym.THEN); }
"else" {return new Symbol(sym.ELSE); }
"fi" {return new Symbol(sym.FI);}
"while" {return new Symbol(sym.WHILE); }
"do" {return new Symbol(sym.DO); }
"od" {return new Symbol(sym.OD); }
"proc" {return new Symbol(sym.PROC); }
"end" {return new Symbol(sym.END); }
"repeat" {return new Symbol(sym.REPEAT); }
"until" {return new Symbol(sym.UNTIL); }
"cons" {return new Symbol(sym.CONS); }
"car" {return new Symbol(sym.CAR); }
"cdr" {return new Symbol(sym.CDR);}
"intp" {return new Symbol(sym.INTP);}
"nullp" {return new Symbol(sym.NULLP); }
"listp"	{return new Symbol(sym.LISTP); }
"return" {return new Symbol(sym.RETURN); }
[0-9]+ {return new Symbol(sym.NUMBER, new Integer(yytext())); }
[a-z|A-Z]+ {return new Symbol(sym.ID, new String(yytext())); }
[ \t\r\n\f] {/* ignore white space */}
. {System.err.println("Illegal character: "+yytext());}
