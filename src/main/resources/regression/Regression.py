from __future__ import print_function
import argparse as args
import numpy as np
import pandas as ps
from sklearn.preprocessing import PolynomialFeatures
from sklearn.metrics import r2_score
from sklearn.model_selection import train_test_split
import matplotlib.pyplot as plt

import sys


def print(s, end='\n', file=sys.stdout):
    file.write(s + end)
    file.flush()


class LR:
    degree = None
    weight = None

    def fit(self, degree, X, y):
        self.degree = degree
        f = PolynomialFeatures(self.degree).fit_transform(X).transpose()
        inv = np.linalg.inv(np.dot(f, f.transpose()))
        self.weight = np.dot(np.dot(inv, f), y)

    def predict(self, X):
        f = PolynomialFeatures(self.degree).fit_transform(X)
        return np.dot(f, self.weight)


def deg_draw(X, y, degrees, split_value=0.2, save='result_regression_{}.png'):
    X_train, X_test, y_train, y_test = \
        train_test_split(X, y, test_size=split_value)

    min_x, max_x = np.min(X), np.max(X)

    def draw(degree, weight):
        point = np.linspace(min_x, max_x)
        f_point = PolynomialFeatures(degree=degree) \
            .fit_transform(point.reshape(point.shape[0], 1))
        plt.plot(point, np.dot(f_point, weight), color='red')

    lin_reg = LR()
    legend = []
    plt.scatter(X, y)
    for deg in degrees:
        lin_reg.fit(degree=deg, X=X_train, y=y_train)
        pred = lin_reg.predict(X_test)
        print("Dergee = {} , Score(R2) = {}".format(deg, r2_score(y_test, pred)))
        draw(degree=deg, weight=lin_reg.weight)
        legend.append("deg = {}".format(deg))
    plt.legend(legend)
    plt.savefig(save.format(degrees[0]))
    plt.show()


def main():
    argument = args.ArgumentParser(description="Regression")
    argument.add_argument('-data', '--data', dest="data", help='File with dataset into csv')
    argument.add_argument('-degree', '--degree', dest="degree", help='Degree polynomial feature')
    argument.add_argument('-split', '--split', dest="split", help='Part of split data')

    parser = argument.parse_args()
    if parser.data is None:
        argument.print_help()
        exit()

    dataset_file = parser.data
    print("use %s file .... " % dataset_file)

    data = ps.read_csv(dataset_file)
    if data.shape[1] != 2:
        print("use 2d point in regression")
        exit()

    if parser.degree is None:
        print("input degree in None")
        exit()

    degree = int(parser.degree)

    X = data.drop('y', axis=1).as_matrix()
    y = data['y'].as_matrix()

    split_value = 0.2
    if parser.split is not None:
        split_value = float(parser.split)

    deg_draw(X, y, degrees=[degree], split_value=split_value)

if __name__ == '__main__':
    main()
