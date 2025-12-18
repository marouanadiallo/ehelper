const path = require('path');
const { EsbuildPlugin } = require('esbuild-loader');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const TsconfigPathsPlugin = require('tsconfig-paths-webpack-plugin');
const { WebpackManifestPlugin } = require('webpack-manifest-plugin');
const WarningsToErrorsPlugin = require('warnings-to-errors-webpack-plugin');


module.exports = (env, argv) => ({
  entry: {
    main: 'ts/main.ts',
    users: 'ts/users.ts'
  },
  output: {
    path: path.resolve(__dirname, './target/classes/static'),
    filename: 'js/[name].[contenthash].bundle.js',
    clean: true
  },
  devtool: argv.mode === 'production' ? false : 'eval-source-map',
  performance: {
    maxEntrypointSize: 488000,
    maxAssetSize: 488000
  },
  optimization: {
   minimize: true,
   minimizer: [
     new EsbuildPlugin({
       target: 'es2015',
       css: true,
       exclude: /alpinejs/
     })
   ]
  },
  plugins: [
     new MiniCssExtractPlugin({
          filename: "[name].[contenthash].css",
     }),
     new WebpackManifestPlugin({
         fileName: '../manifest.json',
         publicPath: '/'
     }),
     new WarningsToErrorsPlugin()
  ],
  module: {
    rules: [
      {
        test: /\.[jt]sx?$/,
        exclude: /node_modules/,
        use: {
          loader: 'esbuild-loader',
          options: {
            target: 'es2015'
          }
        }
      },
      {
        test: /\.css$/,
        use: [
          argv.mode === 'production' ? MiniCssExtractPlugin.loader : 'style-loader',
          {
            loader: 'css-loader',
            options: {
              importLoaders: 1,
              sourceMap: true
            }
          },
          "postcss-loader"
        ]
      }
    ]
  },
  resolve: {
    plugins: [new TsconfigPathsPlugin({})],
    extensions: ['.js', '.jsx', '.ts', '.tsx', '.json']
  },
  devServer: {
    port: 8081,
    compress: true,
    hot: true,
    static: false,
    watchFiles: [
      'src/main/resources/templates/**/*.html',
      'src/main/resources/ts/**/*.ts',
      'src/main/resources/scss/**/*.scss'
    ],
    proxy: [
      {
        context: '**',
        target: 'http://localhost:8080',
        secure: false,
        prependPath: false,
        headers: {
          'X-Devserver': '1',
        }
      }
    ]
  }
});
