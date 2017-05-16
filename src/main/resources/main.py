import numpy as np

import tflearn
from sklearn.utils import shuffle
from tflearn.layers.core import input_data, dropout, fully_connected
from tflearn.layers.conv import conv_2d, max_pool_2d
from tflearn.layers.estimator import regression

tflearn.activations
from sklearn.model_selection import train_test_split


def dense_to_one_hot(labels_dense, num_classes=10):
    num_labels = labels_dense.shape[0]
    index_offset = np.arange(num_labels) * num_classes
    labels_one_hot = np.zeros((num_labels, num_classes))
    labels_one_hot.flat[index_offset + labels_dense.ravel()] = 1
    return labels_one_hot


def task_1(activaction_='tanh'):
    network = input_data(shape=[None, 28, 28, 1], name='input')
    network = fully_connected(network, n_units=1024 * 4, activation='tanh')
    network = fully_connected(network, n_units=1024 * 2, activation=activaction_)
    network = fully_connected(network, n_units=1024 * 1, activation=activaction_)
    network = fully_connected(network, 10, activation='softmax')
    network = regression(network, optimizer='sgd', learning_rate=0.001, loss='categorical_crossentropy', name='target')
    return network


def task_2(activaction_='tanh'):
    network = input_data(shape=[None, 28, 28, 1], name='input')
    network = conv_2d(network, nb_filter=3, filter_size=3, strides=1, activation='relu')
    network = fully_connected(network, 1024 * 4, activation=activaction_)
    network = fully_connected(network, 1024 * 2, activation=activaction_)
    network = fully_connected(network, 1024 * 1, activation=activaction_)
    network = fully_connected(network, 10, activation='softmax')
    network = regression(network, optimizer='adam', learning_rate=0.001, loss='categorical_crossentropy', name='target')
    return network


def task_3():
    network = input_data(shape=[None, 28, 28, 1], name='input')

    network = conv_2d(network, nb_filter=3, filter_size=3, strides=1, activation='relu')
    network = max_pool_2d(network, kernel_size=3, strides=2)

    network = conv_2d(network, nb_filter=3, filter_size=3, strides=1, activation='relu')
    network = max_pool_2d(network, kernel_size=3, strides=2)

    network = fully_connected(network, 1024 * 4, activation='relu')
    network = fully_connected(network, 1024 * 2, activation='relu')
    network = fully_connected(network, 1024 * 1, activation='relu')

    network = fully_connected(network, 10, activation='softmax')
    network = regression(network, optimizer='adam', learning_rate=0.01, loss='categorical_crossentropy', name='target')
    return network


def dop_task_1(drop = 0.2):
    network = input_data(shape=[None, 28, 28, 1], name='input')

    network = conv_2d(network, nb_filter=3, filter_size=3, strides=1, activation='relu')
    network = max_pool_2d(network, kernel_size=3, strides=2)

    network = conv_2d(network, nb_filter=3, filter_size=3, strides=1, activation='relu')
    network = max_pool_2d(network, kernel_size=3, strides=2)

    network = fully_connected(network, 1024 * 4, activation='relu')
    network = dropout(network, 1 - drop)
    network = fully_connected(network, 1024 * 2, activation='relu')
    network = dropout(network, 1 - drop)
    network = fully_connected(network, 1024 * 1, activation='relu')

    network = fully_connected(network, 10, activation='softmax')
    network = regression(network, optimizer='adam', learning_rate=0.001, loss='categorical_crossentropy', name='target')
    return network


def fit_and_predict(net, data, label):
    model = tflearn.DNN(net, tensorboard_verbose=3, tensorboard_dir='./learn_logs/')
    model.fit({'input': data["X_train"]}, {'target': data["y_train"]},
              validation_set=({'input': data["X_test"]}, {'target': data["y_test"]}),
              n_epoch=30,
              snapshot_step=20,
              show_metric=True,
              run_id=label,
              batch_size=256)


def main():
    data = np.load("/home/kate/SE_16-18/ML-TOOL/notMNIST.pickle", allow_pickle=True)

    X, Y = data['test_dataset'], data['test_labels']
    X_val, y_val = data['valid_dataset'][:1000], data['valid_labels'][:1000]
    del data

    # Split data on train and test
    X, Y = shuffle(X, Y)
    X_train, X_test, y_train, y_test = train_test_split(X, Y, random_state=42, test_size=0.2, stratify=Y)
    del X, Y

    X_train = X_train.reshape([-1, 28, 28, 1])
    y_train = dense_to_one_hot(y_train, 10)

    X_test = X_test.reshape([-1, 28, 28, 1])
    y_test = dense_to_one_hot(y_test)

    X_val = X_val.reshape([-1, 28, 28, 1])
    y_val = dense_to_one_hot(y_val)

    data_dict = {
        "X_train": X_train,
        "y_train": y_train,
        "X_test": X_test,
        "y_test": y_test,
        "X_val": X_val,
        "y_val": y_val
    }

    # for activation in ['sigmoid']: #, 'relu', 'tanh'
    #     fit_and_predict(task_1(activation), data_dict, "_".join(["task_1", activation]))

    # fit_and_predict(task_2(), data_dict, "_".join(["task_2", "tanh"]))


    # fit_and_predict(task_3(), data_dict, "_".join(["task_3", ""]))

    # drop = np.linspace(0.05,0.55,10)
    # for i in drop:

    i = 0.5
    fit_and_predict(dop_task_1(i), data_dict, "_".join(["dop_task_{}".format(i), ""]))


    # Run in terminal CUDA_VISIBLE_DEVICES="" to disable GPU
    # fit_and_predict(task_3(), data_dict, "_".join(["task_3_withoutCUDA", ""]))


if __name__ == '__main__':
    main()
