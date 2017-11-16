# PHP代码规范



---
由于后台和安卓端的开发在于数据的衔接，而API由后台开发人员直接给出，故PHP的代码规范和JAVA的代码规范独立开来，但有必要对数据变量做个统一，故我们制定了的JAVA和PHP开发人员应该共同遵循的编码规范：
**数据库表和字段/变量的命名均以小写字母+下划线命名，例如ci_suer表，user_name字段，avatar_path（头像路径）**


由于参与PHP的人员有三个，因此我们制定了统一的PHP开发规范。
##文件格式
文件应该保存为 Unicode（UTF-8）编码格式，不要使用 字节序标记（BOM），和 UTF-16 和 UTF-32 不一样， UTF-8 编码格式的文件不需要指定字节序。而且 BOM 会在 PHP 的输出中产生副作用， 它会阻止应用程序设置它的头信息。另外，所有的换行符应该使用 Unix 格式换行符（LF）。

##PHP 结束标签
PHP 结束标签 ?> 对于 PHP 解析器来说是可选的，但是只要使用了，结束标签之后的空格 有可能会导致不想要的输出，这个空格可能是由开发者或者用户又或者 FTP 应用程序引入的， 甚至可能导致出现 PHP 错误，如果配置了不显示 PHP 错误，就会出现空白页面。基于这个原因， 所有的 PHP 文件将不使用结束标签，而是以一个空行代替。




##文件的命名
类文件的命名必须以大写字母开头，其他文件（配置文件，视图，一般的脚本文件等）的命名是全小写。

例如：
>Somelibrary.php
Some_library.php
applicationconfig.php
application_config.php

另外，类文件的名称必须和类的名称保持一致，例如，如果你有一个类名为 Myclass ， 那么文件名应该是 Myclass.php 。

##类和方法的命名
类名必须以大写字母开头，多个单词之间使用下划线分割，不要使用驼峰命名法。


```PHP
class Super_class {

    public function __construct()
    {

    }
}
```
类的方法应该使用全小写，并且应该明确指出该方法的功能，最好包含一个动词。 避免使用冗长的名称，多个单词之间使用下划线分割。

错误的:

```PHP
function fileproperties()       // not descriptive and needs underscore separator
function fileProperties()       // not descriptive and uses CamelCase
function getfileproperties()        // Better!  But still missing underscore separator
function getFileProperties()        // uses CamelCase
function get_the_file_properties_from_the_file()    // wordy
```
正确的:

```PHP
function get_file_properties()  // descriptive, underscore separator, and all lowercase letters
```

##变量的命名
变量的命名规则和类方法的命名规则非常接近，使用全小写，使用下划线分割， 并且应该明确指出该变量的用途。非常短的无意义的变量只应该在 for 循环中作为迭代器使用。

错误的:
```PHP
$j = 'foo';     // single letter variables should only be used in for() loops
$Str            // contains uppercase letters
$bufferedText       // uses CamelCasing, and could be shortened without losing semantic meaning
$groupid        // multiple words, needs underscore separator
$name_of_last_city_used // too long

```
正确的:

```PHP
for ($j = 0; $j < 10; $j++)
$str
$buffer
$group_id
$last_city

```


##注释
通常情况下，应该多写点注释，这不仅可以向那些缺乏经验的程序员描述代码的流程和意图， 而且当你几个月后再回过头来看自己的代码时仍能帮你很好的理解。 注释并没有强制规定的格式，但是建议以下的形式。

DocBlock 风格的注释，写在类、方法和属性定义的前面，可以被 IDE 识别:

```PHP
/**
 * Super Class
 *
 * @package Package Name
 * @subpackage  Subpackage
 * @category    Category
 * @author  Author Name
 * @link    http://example.com
 */
class Super_class {
/**
 * Encodes string for use in XML
 *
 * @param   string  $str    Input string
 * @return  string
 */
function xml_encode($str)
/**
 * Data for class manipulation
 *
 * @var array
 */
public $data = array();

```
单行注释应该和代码合在一起，大块的注释和代码之间应该留一个空行。

```PHP
// break up the string by newlines
$parts = explode("\n", $str);

// A longer comment that needs to give greater detail on what is
// occurring and why can use multiple single-line comments.  Try to
// keep the width reasonable, around 70 characters is the easiest to
// read.  Don't hesitate to link to permanent external resources
// that may provide greater detail:
//
// http://example.com/information_about_something/in_particular/

$parts = $this->foo($parts);
```


##常量
常量遵循和变量一样的命名规则，除了它需要全部大写。
错误的:

```PHP
myConstant  // missing underscore separator and not fully uppercase
N       // no single-letter constants
S_C_VER     // not descriptive
$str = str_replace('{foo}', 'bar', $str);   // should use LD and RD constants
```
正确的:
```PHP
MY_CONSTANT
NEWLINE
SUPER_CLASS_VERSION
$str = str_replace(LD.'foo'.RD, 'bar', $str);
```
TRUE、FALSE 和 NULL
TRUE 、 FALSE 和 NULL 这几个关键字全部使用大写。


##逻辑操作符
不要使用 || 操作符，它在一些设备上看不清（可能看起来像是数字 11）， 使用 && 操作符比使用 AND 要好一点，但是两者都可以接受。 另外，在 ! 操作符的前后都应该加一个空格。

错误的:
```PHP
if ($foo || $bar)
if ($foo AND $bar)  // okay but not recommended for common syntax highlighting applications
if (!$foo)
if (! is_array($foo))
```
正确的:
```PHP
if ($foo OR $bar)
if ($foo && $bar) // recommended
if ( ! $foo)
if ( ! is_array($foo))
```
##对返回值进行比较以及类型转换
有一些 PHP 函数在失败时返回 FALSE ，但是也可能会返回 "" 或 0 这样的有效值， 这些值在松散类型比较时和 FALSE 是相等的。所以当你在条件中使用这些返回值作比较时， 一定要使用严格类型比较，确保返回值确实是你想要的，而不是松散类型的其他值。

在检查你自己的返回值和变量时也要遵循这种严格的方式，必要时使用 === 和 !== 。

错误的:
```PHP
// If 'foo' is at the beginning of the string, strpos will return a 0,
// resulting in this conditional evaluating as TRUE
if (strpos($str, 'foo') == FALSE)
```
正确的:
```PHP
if (strpos($str, 'foo') === FALSE)
```
错误的:
```PHP
function build_string($str = "")
{
    if ($str == "") // uh-oh!  What if FALSE or the integer 0 is passed as an argument?
    {

    }
}
```
正确的:
```PHP
function build_string($str = "")
{
    if ($str === "")
    {

    }
}
```

##调试代码
不要在你的提交中包含调试代码，就算是注释掉了也不行。 像 var_dump() 、 print_r() 、 die() 和 exit() 这样的函数，都不应该包含在你的代码里， 除非它们用于除调试之外的其他特殊用途。




##一个类一个文件
除非几个类是*紧密相关的*，否则每个类应该单独使用一个文件。

##空格
在代码中使用制表符（tab）来代替空格，这虽然看起来是一件小事，但是使用制表符代替空格， 可以让开发者阅读你代码的时候，可以根据他们的喜好在他们的程序中自定义缩进。 此外还有一个好处是，这样文件可以更紧凑一点，也就是本来是四个空格字符， 现在只要一个制表符就可以了。



##代码缩进
使用 Allman 代码缩进风格。除了类的定义之外，其他的所有大括号都应该独占一行， 并且和它对应的控制语句保持相同的缩进。
```PHP
//错误的:

function foo($bar) {
    // ...
}

foreach ($arr as $key => $val) {
    // ...
}

if ($foo == $bar) {
    // ...
} else {
    // ...
}

for ($i = 0; $i < 10; $i++)
    {
    for ($j = 0; $j < 10; $j++)
        {
        // ...
        }
    }


//正确的:

function foo($bar)
{
    // ...
}

foreach ($arr as $key => $val)
{
    // ...
}

if ($foo == $bar)
{
    // ...
}
else
{
    // ...
}

for ($i = 0; $i < 10; $i++)
{
    for ($j = 0; $j < 10; $j++)
    {
        // ...
    }
}



```
##中括号和小括号内的空格
一般情况下，使用中括号和小括号的时候不应该使用多余的空格。 唯一的例外是，在那些接受一个括号和参数的 PHP 的控制结构（declare、do-while、elseif、for、 foreach、if、switch、while）的后面应该加一个空格，这样做可以和函数区分开来，并增加可读性。
错误的:
```PHP
$arr[ $foo ] = 'foo';
```
正确的
```PHP
$arr[$foo] = 'foo'; // no spaces around array keys
```
错误的:
```PHP
function foo ( $bar )
{

}
```
正确的:
```PHP
function foo($bar) // no spaces around parenthesis in function declarations
{

}
```
错误的:
```PPHP
foreach( $query->result() as $row )
```
正确的:
```PHP
foreach ($query->result() as $row) // single space following PHP control structures, but not in interior parenthesis
```

##PHP 错误
运行代码时不应该出现任何错误信息，并不是把警告和提示信息关掉来满足这一点。 例如，绝不要直接访问一个你没设置过的变量（例如，$_POST 数组）， 你应该先使用 isset() 函数判断下。

确保开发环境对所有人都开启了错误报告，PHP 环境的 display_errors 参数也开启了， 你可以通过下面的代码来检查:
```PHP
if (ini_get('display_errors') == 1)
{
    exit "Enabled";
}
```
有些服务器上 display_errors 参数可能是禁用的，而且你没有权限修改 php.ini 文件， 你可以使用下面的方法来启用它:
```PHP
ini_set('display_errors', 1);
```

##短标记
使用 PHP 的完整标记，防止服务器不支持短标记（ short_open_tag ）参数。

错误的:
```PHP
<? echo $foo; ?>

<?=$foo?>
```
正确的:
```
<?php echo $foo; ?>
```

##每行只有一条语句
切记不要在同一行内写多条语句。

错误的:
```PHP
$foo = 'this'; $bar = 'that'; $bat = str_replace($foo, $bar, $bag);
```
正确的:
```PHP
$foo = 'this';
$bar = 'that';
$bat = str_replace($foo, $bar, $bag);
```
##字符串
字符串使用单引号引起来，当字符串中有变量时使用双引号，并且使用大括号将变量包起来。 另外，当字符串中有单引号时，也应该使用双引号，这样就不用使用转义符。

错误的:
```PHP
"My String"                 // no variable parsing, so no use for double quotes
"My string $foo"                // needs braces
'SELECT foo FROM bar WHERE baz = \'bag\''   // ugly
```
正确的:
```PHP
'My String'
"My string {$foo}"
"SELECT foo FROM bar WHERE baz = 'bag'"
```
##SQL 查询
SQL 关键字永远使用大写：SELECT、INSERT、UPDATE、WHERE、AS、JOIN、ON、IN 等。

考虑到易读性，把长的查询分成多行，最好是每行只有一个从句或子从句。

错误的:
```PHP
// keywords are lowercase and query is too long for
// a single line (... indicates continuation of line)
$query = $this->db->query("select foo, bar, baz, foofoo, foobar as raboof, foobaz from exp_pre_email_addresses
...where foo != 'oof' and baz != 'zab' order by foobaz limit 5, 100");
```
正确的:
```PHP
$query = $this->db->query("SELECT foo, bar, baz, foofoo, foobar AS raboof, foobaz
                FROM exp_pre_email_addresses
                WHERE foo != 'oof'
                AND baz != 'zab'
                ORDER BY foobaz
                LIMIT 5, 100");
```
