var OnlineLessCompiler = {
	"examples": {
		"variables": "// Declaring variables\n@border-width: 1px;\n@red: #842210;\n\n// Using variables\ndiv#header {\n    border: @border-width solid @red;\n} ",
		"mixins": "// Mixins allow you to reuse the contents\n// of a certain ruleset in another ruleset\n\n// The bordered class\n.bordered {\n    border-top: dotted 1px black;\n    border-bottom: solid 2px black;\n}\n\n// We reuse the contents of the bordered class\n// in #menu a and .post a\n#menu a {\n    color: #111;\n    .bordered;\n}\n.post a {\n    color: red;\n    .bordered;\n}",
		"parametric_mixins": "// LESS has a special type of ruleset which\n// acts sort of like a function: the\n// parametric mixin\n\n//The parametric mixin '.border-radius'\n.border-radius (@radius) {\n    border-radius: @radius;\n    -moz-border-radius: @radius;\n    -webkit-border-radius: @radius;\n}\n\n// Which can be used like this:\n#header {\n    .border-radius(4px);\n}\n.button {\n    .border-radius(6px);  \n}\n\n\n// A parametric mixin with a default value:\n.border-radius-with-default(@radius: 5px) {\n    border-radius: @radius;\n    -moz-border-radius: @radius;\n    -webkit-border-radius: @radius;\n}\n\n// Which can be used like this:\n#content {\n    .border-radius-with-default;\n}\n\n// You can also use a parametric mixin\n// without any parameters. This is useful if\n// you don't want the original mixin itself \n// in the css output\n.wrap () {\n    text-wrap: wrap;\n    white-space: pre-wrap;\n    white-space: -moz-pre-wrap;\n    word-wrap: break-word;\n}\npre { \n    .wrap; \n}",
		"nested_rules": "// In LESS you can nest your rulesets.\n// This is a very important feature because:\n// -You don't have to write out repeating long\n//  selectors\n// -It helps to structurize your code\n\n#content {\n    width: 500px;\n\n    a {\n        color: white;\n\n        // You can use the '&'-selector to\n        // apply a nested selector to the\n        // parent selector:\n        &:hover {\n            color: blue;\n        }\n\n        &.selected {\n            color: yellow\n        }\n    }\n}",
		"operations": "// In LESS you can operate on any number,\n// color or variable.\n\n// Basic operations\n@base: 200px;\n@height: @base * 2;\n@min-width: @base + 100;\n@max-width: (@min-width - 50) * 2;\n\n.basic_operations {\n    height: @height;\n    min-width: @min-width;\n    max-width: @max-width;\n}\n\n// Operating on colors:\n@base-color: #444;\n\n.color_operations {\n    color: @base-color / 4;\n    background-color: @base-color + #111;\n}",
		"color_functions": "// LESS provides a variety of functions which\n// transform colors. Colors are first\n// converted to the HSL color-space, and then\n// manipulated at the channel level.\n\n// In the following examples we set \n// @new-color to the result of a\n// transformation on @color\n\n@color: #444;\n\n// Lighter or darker colors \n// (using percentages):\n@new-color: lighten(@color, 10%);\n@new-color: darken(@color, 10%);\n\n// More or less saturated colors\n// (using percentages)\n@new-color: saturate(@color, 10%);    \n@new-color: desaturate(@color, 10%);\n\n// Less or more transparent colors\n// (using percentages) \n@new-color: fadein(@color, 10%);     \n@new-color: fadeout(@color, 10%);\n\n// Larger or smaller hue\n// (using degrees)\n@new-color: spin(@color, 10);         \n@new-color: spin(@color, -10);\n\n\n// You can also extract color information:\n@hue: hue(@color);\n@saturnation: saturation(@color);\n@lightness: lightness(@color);\n\n// Which is useful if you want to create a\n// new colour using one or more properties\n// from the old colour.\n\n// Create @new-color with @color's hue, and\n// its own saturation and lightness.\n@new-colour: hsl(hue(@color), 45%, 90%);\n\n\n// Using the functions to set a property\n// works as expected:\n@base: #f04615;\n\n.class {\n  color: saturate(@base, 5%);\n  background-color: lighten(spin(@base, 8), 25%);\n}",
		"namespaces": "// In LESS you can use namespaces. This can\n// help you to organize your code, and it\n// offers you encapsulation.\n\n// We declare the '.button'-mixin in the\n// '#myNamespace' namespace.\n#myNamespace {\n    .button () {\n        display: block;\n        border: 1px solid black;\n        background-color: grey;\n        &:hover { \n            background-color: white;\n        }\n    }\n}\n\n// Using the '.button'-mixin:\n#header a {\n    color: red;\n    #myNamespace > .button();\n}",
		"scope": "// Scope in LESS is very similar to that of\n// programming languages. Variables and mixins\n// are first looked up locally, and if they\n// aren't found, the compiler will look in the\n// parent scope, and so on.\n\n@var: red;\n\n#page {\n  @var: white;\n  #header {\n    color: @var; // white\n  }\n}\n\n#footer {\n  color: @var; // red  \n}",
		"comments": "// CSS-style comments are preserved by LESS,\n// LESS-style comments are 'silent', they\n// don't show up in the compiled CSS output.\n\n/* Hello, I'm a CSS-style comment */\n// Hi, I'm a silent comment, I won't show up\n// in your CSS\n.class { color: black }",
		"importing": "// You can import .less files, and all the\n// variables and mixins in them will be made\n// available to the main file. The .less\n// extension is optional, so both of these are\n// valid:\n\n// @import \"lib.less\";\n// @import \"lib\";\n\n// If you want to import a CSS file, and don't\n// want LESS to process it, just use the .css\n// extension:\n\n// @import \"lib.css\";\n\n\n// And to show that it works we import the\n// winLESS.org LESS file:\n@import \"/style/less/main\";",
		"string_interpolation": "// Variables can be embeded inside strings in\n// a similar way to ruby or PHP, with the\n// @{name} construct:\n\n@base-url: \"http://assets.fnord.com\";\n.container {\n    background-image: url(\"@{base-url}/images/bg.png\");\n}",
		"escaping": "// Sometimes you might need to output a CSS\n// value which is either not valid CSS syntax,\n// or uses propriatery syntax which LESS\n// doesn't recognize.\n\n// To output such value, we place it inside a\n// string prefixed with ~, for example:\n\n.class {\n  filter: ~\"progid:DXImageTransform.Microsoft.AlphaImageLoader(src='image.png')\";\n}"
	},
	
	"init": function(){
        OnlineLessCompiler.LessEditor = CodeMirror.fromTextArea($("#lessInput textarea")[0], {
            mode: "less",
            lineNumbers: true,
            matchBrackets: true
        });
        OnlineLessCompiler.LessEditor.on("change", OnlineLessCompiler.compile);

        OnlineLessCompiler.CssEditor = CodeMirror.fromTextArea($("#cssOutput textarea")[0], {
            mode: "css",
            lineNumbers: true,
            readOnly: true
        });

        OnlineLessCompiler.loadExample("variables");

        $("#lessEditor").splitter();
    },
	
	"loadExample": function(example){
		OnlineLessCompiler.LessEditor.setValue(OnlineLessCompiler.examples[example]);
		$("#lessExamples a.selected").removeClass("selected");
		$("#lessExamples a." + example).addClass("selected");
		OnlineLessCompiler.compile();
	},
	
	"compile": function(){
		var lessCode = OnlineLessCompiler.LessEditor.getValue();
		var parser = window.less.Parser();
		
		try {
			parser.parse(lessCode, function(error, result){
				if(!error){
                    $("#lessError").hide();
                    OnlineLessCompiler.CssEditor.setValue(result.toCSS());
				}
				else{
					showError(error);
				}
			});
		}
		catch (error){
			showError(error);
		}

        function showError(error){
            var errorMessage = "";
            if(error){
                if(error.name){
                    errorMessage += error.name + ": ";
                }
                if(error.message){
                    errorMessage += error.message;
                }
            }
            if(errorMessage === ""){
                errorMessage = "Unknown error";
            }
            $("#lessError").text(errorMessage);
            $("#lessError").show();
        }
	}
};

$(document).ready(function(){
    if($('body').hasClass('online-less-compiler')){
        OnlineLessCompiler.init();
    }
});