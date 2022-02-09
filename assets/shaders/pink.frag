#version 150
precision highp float;

//Inputs
uniform sampler2D tex;

uniform vec3 rgb;

void main(void) {
	gl_FragColor = vec4(rgb, 1.0);
}
