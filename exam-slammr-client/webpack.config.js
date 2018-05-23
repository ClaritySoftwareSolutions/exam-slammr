const path = require('path');

const BUILD_DIR = path.resolve(__dirname, 'dist');
const SRC_DIR = path.resolve(__dirname, 'src');

const ExtractTextPlugin = require("extract-text-webpack-plugin");
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CleanWebpackPlugin = require('clean-webpack-plugin');

const extractSass = new ExtractTextPlugin({
    filename: "[name].[hash].css",
    disable: process.env.NODE_ENV === "development"
});

const htmlWebpack = new HtmlWebpackPlugin({
    template: SRC_DIR + '/index.ejs'
});

const cleanWebpack = new CleanWebpackPlugin(['dist']);

let config = module.exports;

config.entry = SRC_DIR + '/js/index.jsx';
config.output = {
    path: BUILD_DIR,
    filename: 'bundle.[hash].js'
};

config.resolve = {};

config.module = {
    loaders: [
        {
            test: /\.jsx?/,
            include: SRC_DIR,
            loader: 'babel-loader'
        },
        {
            test: /\.css$/,
            use: ['style-loader', 'css-loader']
        },
        {
            test: /\.(woff2?|ttf|eot|svg)$/,
            loader: 'file-loader?name=fonts/[name].[ext]'
        },
        {
            test: /\.scss$/,
            use: extractSass.extract({
                use: [
                    {
                        loader: "css-loader" // translates CSS into CommonJS
                    },
                    {
                        loader: "sass-loader" // compiles Sass to CSS
                    }
                ],
                fallback: "style-loader" // use style-loader in development
            })
        }
    ]
};

config.plugins = [
    cleanWebpack,
    extractSass,
    htmlWebpack
];

config.devServer = {
    contentBase: SRC_DIR,
    compress: false,
    quiet: false,
    port: 8080
};

